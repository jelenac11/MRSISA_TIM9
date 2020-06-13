Vue.component('pretraga-lekara', {
	data : function() {
		return {
			ulogovan : {},
			klinika: {},
			lekari : [],
			token: "",
			ocenaFilter : false,
			ocenaDonja: 0,
			ocenaGornja: 0,
			imePrezime: "",
			tip: "",
			vreme: "",
			id: 0,
			termini : [],
			dijalogGreska: false,
			predefinisani: [],
			lekar: 0,
			potvrda: {},
			ocena: {},
			izabraniLekar: {},
			mozeDaOceniKliniku: false,
			mozeDaOceniLekara: false,
		} 
	},
	template: `
		<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
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
				    <h6 class="kartica-subtitle mb-2 text-muted">{{ klinika.lokacija }}</h6>
				    <p class="kartica-text">{{ klinika.opis }}</p>
				  </div>
				</div>
				<div class="input-group">
					<span class="input-group-btn">
				    	<a style="color:white" class="btn btn-primary my-2 mx-2" data-toggle="collapse" href="#predefinisaniTermini" role="button">
					    	Brzo zakazivanje
					  	</a>
				  	</span>
				  	<span class="input-group-btn">
				    	<a style="color:white" class="btn btn-info my-2 mr-2" data-toggle="collapse" href="#filteriLekari" role="button">
					    	Prikaži filtere
					  	</a>
				  	</span>
					<input type="search" class="form-control col-4 my-2 mr-2" style="height:40px" v-model="imePrezime" placeholder="Ime i prezime..."/>
					<span class="input-group-btn">
						<button class="btn btn-danger mt-2 mr-2" v-on:click="ocisti">
					    	Očisti
					  	</button>
					</span>
				</div>
				<div class="collapse" id="predefinisaniTermini">
					<div class="kartica kartica-body m-2">
			    		<form>
						  	<div class="form-row"> 
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
									      	<td width="10%"><button type="button" class="btn btn-primary" style="color:white" v-on:click="zakazivanjePredefinisanog(indeks)">Zakaži</button></td>
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
					      	<td width="18%" style="text-align:left">
								
					      	</td>
				    	</tr>
				  	</tbody>
				</table>
			</div>
			
			<div class="modal fade" id="prikazSlobodnihTermina" tabindex="-1" role="dialog">
				<div class="modal-dialog modal-lg" role="document">
			    	<div class="modal-content">
			      		<div class="modal-header">
			        		<div class="modal-title">
			        			<h4 class="alignleft">Slobodni termini, {{ izabraniLekar.ime + " " + izabraniLekar.prezime }}:&nbsp</h4>
						    	<p v-if="mozeDaOceniLekara" class="alignright">
									<i class="far fa-star unchecked" v-on:click="oceniLekara('1zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'1zl' + izabraniLekar.id"></i> 
									<i class="far fa-star unchecked" v-on:click="oceniLekara('2zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'2zl' + izabraniLekar.id"></i>
								   	<i class="far fa-star unchecked" v-on:click="oceniLekara('3zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'3zl' + izabraniLekar.id"></i>
								   	<i class="far fa-star unchecked" v-on:click="oceniLekara('4zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'4zl' + izabraniLekar.id"></i>
									<i class="far fa-star unchecked" v-on:click="oceniLekara('5zl' + izabraniLekar.id, izabraniLekar)" v-bind:id="'5zl' + izabraniLekar.id"></i>
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
								      	<td width="20%"><button type="button" style="color:white" class="btn btn-primary" v-on:click="zakazivanjeSlobodnog(te)">Zakaži</button></td>
							    	</tr>
							  	</tbody>
							</table>
				      	</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" style="color:white" data-dismiss="modal">Nazad</button>
			      		</div>
			    	</div>
				</div>
			</div>
			
			<v-dialog v-model="dijalogGreska" max-width="300">
		      <v-card>
		        <v-card-title class="headline">Greška</v-card-title>
		        <v-card-text>Neko drugi je upravo zakazao ovaj termin pre vas.</v-card-text>
		        <v-card-actions>
		          <v-spacer></v-spacer>
		          <v-btn color="green darken-1" text @click="dijalogGreska = false">u redu</v-btn>
		        </v-card-actions>
		      </v-card>
		    </v-dialog>
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
		izaberiLekara: function(lekar) {
			this.lekar = lekar.id;
			var list = [ '1z', '2z', '3z', '4z', '5z' ];
			this.izabraniLekar = lekar;
			axios
	        .post('/pregledi/mozeDaOceniLekara', this.izabraniLekar, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	this.mozeDaOceniLekara = response.data;
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
						}
			        	if (this.izabraniLekar.ocena.ocenjivac != null) {
			        		for (i = 0; i < this.izabraniLekar.ocena.vrednost; i++) {
			        			document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.remove("unchecked");
								document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.add("checked");
								document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.remove("far");
								document.getElementById(list[i] + "l" + this.izabraniLekar.id).classList.add("fas");
			        		}
			        	}
			        })
			        .catch(function (error) { console.log(error); });
				}
	        })
	        .catch(function (error) { console.log(error); });
			axios
			.put("/lekari/vratiSlobodneTermine", {id: lekar.id, datum: this.vreme, tipPregleda: this.tip},  { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {
				this.termini = response.data;
				if (this.termini.length > 0) {
					$("#prikazSlobodnihTermina").modal("show");
				}
			})
		    .catch(function (error) { console.log(error); });
		},
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		zakazivanjePredefinisanog : function(indeks) {
		    this.predefinisani[indeks].pacijent = this.ulogovan;
			this.predefinisani[indeks].lokacija = this.klinika;
			axios
			.put("/pregledi/zakaziPredefinisani", this.predefinisani[indeks], { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {
				console.log(response.data);
				if (!response.data) {
					this.dijalogGreska = true;
				}
				this.dobaviPredefinisane()
			})
		    .catch(function (error) { console.log(error); });
		},
		dobaviPredefinisane: function(){
			axios
	        .put('/pregledi/ucitajPredefinisanePreglede', { id: this.id, datum: this.vreme, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => (this.predefinisani = response.data))
	        .catch(function (error) { console.log(error); });
		},
		zakazivanjeSlobodnog: function(termin) {
			axios
			.put("/pregledi/zakaziTermin", { id: this.lekar, datum: termin, tipPregleda: this.tip, pacijent: this.ulogovan.id, klinika : this.klinika.id}, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {
				this.potvrda = response.data; 
				if(this.potvrda==null){
					toast("Greska!");
					return false;
				}
				$('#iksic').click(); 
				this.$router.push({ name: 'potvrdaZakazivanja', params: {pregled : this.potvrda, zaposleni: false}});})
		    .catch(function (error) { console.log(error); });
		},
		ocisti : function () {
			this.imePrezime = "";
			this.ocenaFilter = false;
			this.ocenaDonja = 0;
			this.ocenaGornja = 0;
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
			this.neOtvarajModal = true;
			var list = [ '1zl' + lekar.id, '2zl' + lekar.id, '3zl' + lekar.id, '4zl' + lekar.id, '5zl' + lekar.id ];
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
			
			let ocenaLekara = {
				id: 0,
				ocenjivac: this.ulogovan,
				lekar: lekar,
				vrednost: list.indexOf(element) + 1,
			}
			
			axios
	        .post('/ocene/oceniLekara', ocenaLekara, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	toast("Ocenili ste lekara " + lekar.ime + " " + lekar.prezime + " ocenom " + ocenaLekara.vrednost);
	        	axios
	        	.get('/lekari/ucitajSveLekareKlinike/' + this.id, { headers: { Authorization: 'Bearer ' + this.token }} )
	            .then(response => {
	            	for (i = 0; i < this.lekari.length; i++) {
	            		for (j = 0; j < response.data.length; j++) {
	            			if (response.data[j].id == this.lekari[i].id) {
		            			this.lekari[i].prosecnaOcena = response.data[j].prosecnaOcena;
		            		}
	            		}
	            	}
	            })
	            .catch(function (error) { console.log(error); });
	        })
	        .catch(function (error) { console.log(error); });
		},
	},
	computed: {
	    filtriraniLekari : function () {
	    	return this.lekari.filter(lekar => {
				return (lekar.ime.toLowerCase().includes(this.imePrezime.toLowerCase().trim()) || lekar.prezime.toLowerCase().includes(this.imePrezime.toLowerCase().trim())) &&
				this.zadovoljavaOcenu(lekar);
	    	})
	    },
	},
	created() {
		this.token = localStorage.getItem("token");
		this.tip = localStorage.getItem("tip");
		this.vreme = localStorage.getItem("vreme");
		this.id = localStorage.getItem("idKlinike");
	},
	mounted() {
		var list = [ '1z', '2z', '3z', '4z', '5z' ];

		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data;})
        .catch(function (error) { console.log(error); });
		axios
        .put('/klinike/ucitajSlobodneLekare', { id: this.id, datum: this.vreme, tipPregleda: this.tip}, { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => {
        	this.lekari = response.data;
        })
        .catch(function (error) { console.log(error); });
		this.dobaviPredefinisane();
		axios
		.get('/klinike/' +  this.id, { headers: { Authorization: 'Bearer ' + this.token }})
		.then(response => {
			this.klinika = response.data
			axios
	        .post('/pregledi/mozeDaOceniKliniku', this.klinika, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
	        	this.mozeDaOceniKliniku = response.data;
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
		})
        .catch(function (error) { console.log(error); });
	},
})