Vue.component('zaposleni', {
	data : function() {
		return {
			ulogovan : {},
			klinika: {lat:0,lng:0},
			lekari : [],
			token: "",
			ocenaFilter : false,
			ocenaDonja: 0,
			ocenaGornja: 0,
			imePrezime: "",
			tip: "",
			vreme: null,
			id: 0,
			termini : [],
			predefinisani: [],
			lekar: 0,
			potvrda: {},
			tipovi: [],
			ocena: {},
			dialog: false,
			izabraniLekar: {},
			mozeDaOceniKliniku: false,
			mozeDaOceniLekara: false,
		} 
	},
	template: `
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar> 
		<v-app>
			<div data-app>
				<div class="naviga tab-pane fade show active" id="pills-pk" role="tabpanel" >
					<div class="kartica mt-2 mx-2" style="width: 98.7%;">
					  <div class="kartica-body">
					  	<div class="kartica-title">
					    	<h4 class="alignleft">{{ klinika.naziv + ", Ocena: " + klinika.ocena }}</h4>
					    	<p v-if="mozeDaOceniKliniku" class="alignright">
								<i class="far fa-star unchecked" v-on:click="oceni('1z')" id="1z"></i> 
								<i class="far fa-star unchecked" v-on:click="oceni('2z')" id="2z"></i>
							   	<i class="far fa-star unchecked" v-on:click="oceni('3z')" id="3z"></i>
							   	<i class="far fa-star unchecked" v-on:click="oceni('4z')" id="4z"></i>
							   	<i class="far fa-star unchecked" v-on:click="oceni('5z')" id="5z"></i>
							</p>
					    	<div style="clear: both;"></div>
					  	</div>
					    <p class="kartica-text">{{ klinika.opis }}</p>
					    <h6 class="kartica-subtitle mb-2 text-muted">Lokacija: {{ klinika.lokacija }}</h6>
					    <div class="d-flex w-20 justify-content-between">									  	
									  		<GmapMap style="width: 600px; height: 300px;" :zoom="15" :center="{
										          lat: klinika.lat,
										          lng: klinika.lng,
										        }">
										      <GmapMarker
										        v-if="klinika"
										        label="★"
										        :position="{
										          lat: klinika.lat,
										          lng: klinika.lng,
										        }"
										        />
										    </GmapMap>
								  	</div>
					  </div>
					</div>
					<p class="m-1 ml-3 mt-2 font-weight-normal">*Za pretragu klinika je neophodno uneti tip pregleda i datum.</p>
					<div class="input-group">
						<span class="input-group-btn">
					    	<a class="btn btn-primary my-2 mx-2" style="color:white" data-toggle="collapse" href="#predefinisaniTermini" role="button" v-on:click="dobaviPredefinisane()">
						    	Brzo zakazivanje
						  	</a>
					  	</span>
					  	<span class="input-group-btn">
					    	<a class="btn btn-info my-2 mr-2" style="color:white"  data-toggle="collapse" href="#filteriLekari" role="button">
						    	Prikaži filtere
						  	</a>
					  	</span>
						<input type="search" class="form-control col-4 my-2 mr-2" style="height:40px" v-model="imePrezime"  placeholder="Ime i prezime..."/>
						<input type="date" v-model="vreme" id="datumID" placeholder="Datum..." style="height:40px" class="form-control col-4 ml-auto my-2 mr-2">
						<select class="custom-select my-2 mr-2" v-model="tip" style="height:40px" id="tip">
							<option value="" disabled selected hidden>Tipovi pregleda...</option>
					    	<option v-for="t in tipovi" :value="t.naziv">
								{{ t.naziv }}
					    	</option>
					  	</select>
					  	<span class="input-group-btn">
							<button class="btn btn-danger mt-2 mr-2" v-on:click="ocisti">
						    	Očisti
						  	</button>
						</span>
					</div>
					<div class="collapse" id="predefinisaniTermini">
						<div class="kartica kartica-body m-2">
				    		<form>
							  	<div class="form-row mb-4"> 
							  		<table class="table table-hover">
									  	<thead class="thead-light">
									    	<tr>
										      	<th scope="col" width="20%">Vreme</th>
										      	<th scope="col" width="7.5%">Sala</th>
										      	<th scope="col" width="25%">Lekar</th>
										      	<th scope="col" width="20%">Tip pregleda</th>
										      	<th scope="col" width="10%">Cena</th>
										      	<th scope="col" width="7.5%">Popust</th>
										      	<th scope="col" width="10%"></th>
									    	</tr>
									  	</thead>
									  	<tbody>
									  		<tr v-for="(predef,indeks) in predefinisani">
										      	<td width="20%">{{ urediDatum(predef.datum) }}</td>
										      	<td width="7.5%">{{ predef.sala}}</td>
										      	<td width="25%">{{ predef.lekar.ime }} {{predef.lekar.prezime}}</td>
										      	<td width="20%">{{ predef.tip.naziv }}</td>
										      	<td width="10%">{{ predef.cena }}</td>
										      	<td width="7.5%">{{ predef.popust + "%" }}</td>
										      	<td width="10%"><button type="button" class="btn btn-primary" v-on:click="zakazivanjePredefinisanog(indeks)">Zakaži</button></td>
									    	</tr>
									  	</tbody>
									</table>
								</div>
							</form>
				  		</div>
					</div>
					<div class="collapse" id="filteriLekari">
				  		<div class="kartica kartica-body m-2">
				    		<form>
							  	<div class="form-row mb-4"> 
							  		<div class="mt-5 mr-3 custom-control custom-checkbox">
							  			<input type="checkbox" class="custom-control-input" id="ocenaFilter" v-model="ocenaFilter">
							  			<label class="custom-control-label" for="ocenaFilter"><font size="4">Ocena</font></label>
									</div>
							  		<div class="col-2 mt-1 mx-3">
							    	 	<label for="odocena">Od</label>
										<input type="number" v-model="ocenaDonja" class="form-control" id="odocena" placeholder="Od">
									</div>
									<div class="col-2 mt-1 mx-3">
							    	 	<label for="doocena">Do</label>
										<input type="number" v-model="ocenaGornja" class="form-control" id="doocena" placeholder="Do">
									</div>
								</div>
							</form>
				  		</div>
					</div>
					<table class="table table-hover table-striped">
					  	<thead class="thead-light">
					    	<tr>
						      	<th scope="col" width="36%">Ime</th>
						      	<th scope="col" width="36%">Prezime</th>
						      	<th scope="col" width="10%">Ocena</th>
						      	<th scope="col" width="18%"></th>
					    	</tr>
					  	</thead>
					  	<tbody>
					  		<tr v-for="le in filtriraniLekari" v-on:click="izaberiLekara(le)">
						      	<td width="36%">{{ le.ime }}</td>
						      	<td width="36%">{{ le.prezime }}</td>
						      	<td width="10%">{{ le.prosecnaOcena }}</td>
						      	<td width="18%" style="text-align:left" v-html="le.zvezdica"></td>
					    	</tr>
					  	</tbody>
					</table>
				</div>

				<div class="modal fade" id="prikazSlobodnihTermina" tabindex="-1" role="dialog">
					<div class="modal-dialog modal-lg" role="document">
				    	<div class="modal-content">
				      		<div class="modal-header">
				        		<div class="modal-title">
				        			<h4 class="alignleft">Slobodni termini, {{ izabraniLekar.ime + " " + izabraniLekar.prezime }}&nbsp</h4>
							    	<p v-if="mozeDaOceniLekara" class="alignright">
										<i class="far fa-star unchecked" v-on:click="oceniLekara('1zls' + izabraniLekar.id, izabraniLekar)" v-bind:id="'1zls' + izabraniLekar.id"></i> 
										<i class="far fa-star unchecked" v-on:click="oceniLekara('2zls' + izabraniLekar.id, izabraniLekar)" v-bind:id="'2zls' + izabraniLekar.id"></i>
									   	<i class="far fa-star unchecked" v-on:click="oceniLekara('3zls' + izabraniLekar.id, izabraniLekar)" v-bind:id="'3zls' + izabraniLekar.id"></i>
									   	<i class="far fa-star unchecked" v-on:click="oceniLekara('4zls' + izabraniLekar.id, izabraniLekar)" v-bind:id="'4zls' + izabraniLekar.id"></i>
										<i class="far fa-star unchecked" v-on:click="oceniLekara('5zls' + izabraniLekar.id, izabraniLekar)" v-bind:id="'5zls' + izabraniLekar.id"></i>
									</p>
							    	<div style="clear: both;"></div>
				        		</div>
				        		<button type="button" class="close" id = "iksic" data-dismiss="modal"><span>&times;</span></button>
				      		</div>
				      		<div class="modal-body">
					        	<table class="table table-hover">
								  	<thead class="thead-light">
								    	<tr>
									      	<th scope="col" width="70%">Termin</th>
									      	<th scope="col" width="30%"></th>
								    	</tr>
								  	</thead>
								  	<tbody>
								  		<tr v-for="te in termini">
									      	<td width="70%">{{ urediDatum(te) }}</td>
									      	<td width="20%"><button type="button" class="btn btn-primary" v-on:click="zakazivanjeSlobodnog(te)">Zakaži</button></td>
								    	</tr>
								  	</tbody>
								</table>
					      	</div>
				      		<div class="modal-footer">
				        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
				      		</div>
				    	</div>
					</div>
				</div>
			
				<v-dialog width="300" v-model="dialog">
					<v-card>
						<v-card-title>
		        			{{ izabraniLekar.ime + " " + izabraniLekar.prezime }}
		        		</v-card-title>
		        		<v-spacer></v-spacer>
		        		<v-list-item-subtitle>
		        			<p class="m-4 h6">Vaša ocena: </p>
		        			<div class="m-4">
		        				<i class="far fa-star unchecked" v-on:click="oceniLekara('1zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'1zl' + izabraniLekar.id"></i> 
								<i class="far fa-star unchecked" v-on:click="oceniLekara('2zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'2zl' + izabraniLekar.id"></i>
							   	<i class="far fa-star unchecked" v-on:click="oceniLekara('3zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'3zl' + izabraniLekar.id"></i>
							   	<i class="far fa-star unchecked" v-on:click="oceniLekara('4zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'4zl' + izabraniLekar.id"></i>
								<i class="far fa-star unchecked" v-on:click="oceniLekara('5zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'5zl' + izabraniLekar.id"></i>
		        			</div>
		        		</v-list-item-subtitle>
					</v-card>
				</v-dialog>
			</div>
		</v-app>
	</div>
	`
	, 
	methods : {
		zadovoljavaOcenu : function (lekar) {
			if (this.ocenaFilter) {
	    		if (lekar.prosecnaOcena >= this.ocenaDonja && lekar.prosecnaOcena <= this.ocenaGornja) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		return true;
	    	}
		},
		slobodanLekar : function (lekar) {
			datum = new Date(this.vreme+"");
	    	timestamp = datum.getTime() - 7200000;
    		axios
    		.put('/lekari/proveriTipIVremeLekara', { id: lekar.id, datum: timestamp, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }})
    		.then(response => {lekar.slobodan = response.data})
            .catch(function (error) { console.log(error); })
		},
		izaberiLekara: function(lekar){
			var list = [ '1z', '2z', '3z', '4z', '5z' ];
			this.izabraniLekar = lekar;
			axios
	        .post('/pregledi/mozeDaOceniLekara', this.izabraniLekar, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	this.mozeDaOceniLekara = response.data;
	        })
	        .catch(function (error) { console.log(error); });
			if (this.mozeDaOceniLekara) {
				axios
		        .post('/ocene/ucitajOcenuPacijentaLekara', this.izabraniLekar, { headers: { Authorization: 'Bearer ' + this.token }} )
		        .then(response => {
		        	if (response.data != null) {
		        		this.$set(this.izabraniLekar, 'ocena', response.data);
		        	}
					for (i = 0; i < 5; i++) {
						document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.remove("checked");
						document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.add("unchecked");
						document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.remove("fas");
						document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.add("far");
						document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.remove("checked");
						document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.add("unchecked");
						document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.remove("fas");
						document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.add("far");
					}
		        	if (this.izabraniLekar.ocena.ocenjivac != null) {
		        		for (i = 0; i < this.izabraniLekar.ocena.vrednost; i++) {
		        			document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.remove("unchecked");
							document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.add("checked");
							document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.remove("far");
							document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.add("fas");
							document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.remove("unchecked");
							document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.add("checked");
							document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.remove("far");
							document.getElementById(list[i] + "ls" + this.izabraniLekar.id).classList.add("fas");
		        		}
		        	}
		        })
		        .catch(function (error) { console.log(error); });
			}
			
			if (this.tip != "" && this.vreme != null) {
				this.lekar = lekar.id;
				datum = new Date(this.vreme+"");
		    	timestamp = datum.getTime() - 7200000;
				axios
				.put("/lekari/vratiSlobodneTermine", {id: lekar.id, datum: timestamp, tipPregleda: this.tip},  { headers: { Authorization: 'Bearer ' + this.token }})
				 .then(response => {
					 this.termini = response.data;
					 if (this.termini.length > 0) {
						 $("#prikazSlobodnihTermina").modal("show");
					 } else {
						 if (this.mozeDaOceniLekara) {
							 this.dialog = true;
						 }
					 }
				 })
			     .catch(function (error) { console.log(error); });
			} else {
				if (this.mozeDaOceniLekara) {
					 this.dialog = true;
				 }
			}
		},
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        sati = date.toLocaleTimeString();
	        return datum + " " + sati
		},
		zakazivanjePredefinisanog : function(indeks) {
		    this.predefinisani[indeks].pacijent = this.ulogovan;
			this.predefinisani[indeks].lokacija = this.klinika;
			axios
			.put("/pregledi/zakaziPredefinisani", this.predefinisani[indeks], { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => (this.dobaviPredefinisane()))
		    .catch(function (error) { console.log(error); });
		},
		dobaviPredefinisane: function(){
			if (this.tip != "" && this.vreme != null) {
				datum = new Date(this.vreme+"");
		    	timestamp = datum.getTime() - 7200000;
				axios
		        .put('/pregledi/ucitajPredefinisanePreglede', { id: this.id, datum: timestamp, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }} )
		        .then(response => (this.predefinisani = response.data))
		        .catch(function (error) { console.log(error); });
			}
		},
		zakazivanjeSlobodnog: function(termin) {
			axios
			.put("/pregledi/zakaziTermin", { id: this.lekar, datum: termin, tipPregleda: this.tip, pacijent: this.ulogovan.id, klinika : this.klinika.id}, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {this.potvrda = response.data; $('#iksic').click(); this.$router.push({ name: 'potvrdaZakazivanja', params: {pregled : this.potvrda, zaposleni: true}});})
		    .catch(function (error) { console.log(error); });
		},
		ocisti : function () {
			this.imePrezime = "";
			this.ocenaFilter = false;
			this.ocenaDonja = 0;
			this.ocenaGornja = 0;
			this.vreme = null;
			this.tip = "";
		},
		oceni : function(element) {
			var list = [ '1z', '2z', '3z', '4z', '5z' ];
			var cls = document.getElementById(element).className;
			if(cls.includes("unchecked")) {
				for (i = 0; i <= list.indexOf(element); i++) {
					document.getElementById(list[i]).classList.remove("unchecked");
					document.getElementById(list[i]).classList.add("checked");
					document.getElementById(list[i]).classList.remove("far");
					document.getElementById(list[i]).classList.add("fas");
				}
			} else {
				for (i = list.indexOf(element) + 1; i < list.length; i++) {
					document.getElementById(list[i]).classList.remove("checked");
					document.getElementById(list[i]).classList.add("unchecked");
					document.getElementById(list[i]).classList.remove("fas");
					document.getElementById(list[i]).classList.add("far");
				}
			}
			this.ocena.id = 0;
			this.ocena.ocenjivac = this.ulogovan;
			this.ocena.klinika = this.klinika;
			this.ocena.vrednost = list.indexOf(element) + 1;
			
			axios
	        .post('/ocene/oceniKliniku', this.ocena, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	toast("Ocenili ste kliniku ocenom " + this.ocena.vrednost);
	        	axios
	    		.get('/klinike/' +  this.id, { headers: { Authorization: 'Bearer ' + this.token }})
	    		.then(response => {
	    			this.klinika = response.data;
	    		})
		        .catch(function (error) { console.log(error); });
	        })
	        .catch(function (error) { console.log(error); });
		},
		oceniLekara : function(element, lekar) {
			var list = [ '1zl' + lekar.id, '2zl' + lekar.id, '3zl' + lekar.id, '4zl' + lekar.id, '5zl' + lekar.id ];
			var list2 = [ '1zls' + lekar.id, '2zls' + lekar.id, '3zls' + lekar.id, '4zls' + lekar.id, '5zls' + lekar.id ];
			var cls = document.getElementById(element).className;
			var ocena = 5;
			if (element[3] == "s") {
				ocena = list2.indexOf(element) + 1;
			} else {
				ocena = list.indexOf(element) + 1;
			}
			if(cls.includes("unchecked")) {
				for (i = 0; i <= ocena - 1; i++) {
					if (element[3] == "s") {
						document.getElementById(list2[i]).classList.remove("unchecked");
						document.getElementById(list2[i]).classList.add("checked");
						document.getElementById(list2[i]).classList.remove("far");
						document.getElementById(list2[i]).classList.add("fas");
					} else {
						document.getElementById(list[i]).classList.remove("unchecked");
						document.getElementById(list[i]).classList.add("checked");
						document.getElementById(list[i]).classList.remove("far");
						document.getElementById(list[i]).classList.add("fas");
					}
				}
			} else {
				for (i = ocena; i < list.length; i++) {
					if (element[3] == "s") {
						document.getElementById(list2[i]).classList.remove("checked");
						document.getElementById(list2[i]).classList.add("unchecked");
						document.getElementById(list2[i]).classList.remove("fas");
						document.getElementById(list2[i]).classList.add("far");
					} else {
						document.getElementById(list[i]).classList.remove("checked");
						document.getElementById(list[i]).classList.add("unchecked");
						document.getElementById(list[i]).classList.remove("fas");
						document.getElementById(list[i]).classList.add("far");
					}
				}
			}
			
			let ocenaLekara = {
				id: 0,
				ocenjivac: this.ulogovan,
				lekar: lekar,
				vrednost: ocena,
			}
			
			axios
	        .post('/ocene/oceniLekara', ocenaLekara, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	toast("Ocenili ste lekara " + lekar.ime + " " + lekar.prezime + " ocenom " + ocenaLekara.vrednost);
	        	axios
	        	.get('/lekari/ucitajSveLekareKlinike/' + this.id, { headers: { Authorization: 'Bearer ' + this.token }} )
	            .then(response => {
	            	this.lekari = response.data;
	            })
	            .catch(function (error) { console.log(error); });
	        })
	        .catch(function (error) { console.log(error); });
		},
	},
	computed: {
	    filtriraniLekari : function () {
	    	return this.lekari.filter(lekar => {
	    		if (this.tip != "" && this.vreme != null) {
	    			sada = new Date().getTime();
	    			if (sada > new Date(this.vreme).getTime() - 7200000) {
	    				return false;
	    			}
	    			this.slobodanLekar(lekar)
					return (lekar.ime.toLowerCase().includes(this.imePrezime.toLowerCase().trim()) || lekar.prezime.toLowerCase().includes(this.imePrezime.toLowerCase().trim())) &&
					this.zadovoljavaOcenu(lekar) && lekar.slobodan;
	    		} else {
	    			return true;
	    		}
	    	})
	    },
	},
	created() {
		this.token = localStorage.getItem("token");
		this.id = this.$route.params.id;
	},
	mounted() {
		var list = [ '1z', '2z', '3z', '4z', '5z' ];

		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data;})
        .catch(function (error) { console.log(error); });
		axios
        .get('/lekari/ucitajSveLekareKlinike/' + this.id, { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => {
        	this.lekari = response.data;
        })
        .catch(function (error) { console.log(error); });
		axios
        .get('/tipoviPregleda/ucitajTipoveKlinike/' + this.id, { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.tipovi = response.data))
        .catch(function (error) { console.log(error); });
		axios
		.get('/klinike/' +  this.id, { headers: { Authorization: 'Bearer ' + this.token }})
		.then(response => {
			this.klinika = response.data
			axios
	        .post('/pregledi/mozeDaOceniKliniku', this.klinika, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	this.mozeDaOceniKliniku = response.data;
	        })
	        .catch(function (error) { console.log(error); });
			axios
	        .post('/ocene/ucitajOcenuPacijentaKlinike', this.klinika, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	if (response.data != null) {
	        		this.ocena = response.data;
	        	}
	        	
	        	if (this.ocena.ocenjivac != null) {
	        		for (i = 0; i < this.ocena.vrednost; i++) {
	        			document.getElementById(list[i]).classList.remove("unchecked");
						document.getElementById(list[i]).classList.add("checked");
						document.getElementById(list[i]).classList.remove("far");
						document.getElementById(list[i]).classList.add("fas");
	        		}
	        	}
	        })
	        .catch(function (error) { console.log(error); });
		})
        .catch(function (error) { console.log(error); });
	},
})