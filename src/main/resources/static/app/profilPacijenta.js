Vue.component("profil-pacijenta", {
	data : function() {
		return {
			token: "",
			korisnik:{},
			pacijent:{},
			izabraniIzvestaj: {
				opis: "",
				dijagnoza: {},
				recepti: [],
				pregled: {},
			},
			noviIzvestaj: {
				opis: "",
				dijagnoza: {},
				recepti: [],
				pregled: {},
			},
			uloga: "",
			sifrarnikLekova: {},
			sifrarnikDijagnoza: {},
			imaBolesti: false,
			nijeObavioNikadPregled: false,
			dijalog: false,
			submitovanoIzvestaj: false,
			submitovanoKarton: false,
			zdravstveniKarton: {},
			noviZdravstveniKarton: {},
			izmena: "",
		} 
	},
	template: 
	`
	<v-app>
		<div class="container d-flex justify-content-center">
			<div class="card mt-5" style="width: 70rem;">
				<h4 class="card-header">Profil pacijenta</h4>
				<div class="card-body">
					
					<div class="form-row">
						<div class="col">
							<h6>Email adresa:</h6>
							<input type="text" v-model="pacijent.email" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<h6>Ime:</h6>
							<input type="text" v-model="pacijent.ime" class="form-control" disabled>
						</div>
				    	<div class="col">
				    		<h6>Prezime:</h6>
							<input type="text" v-model="pacijent.prezime" class="form-control" disabled>
				    	</div>
				    </div>
				    <div class="form-row">
				    	<div class="col">
				    		<h6>Adresa:</h6>
							<input type="text" v-model="pacijent.adresa" class="form-control" disabled>
				    	</div>
				    	<div class="col">
				    		<h6>Grad:</h6>
							<input type="text" v-model="pacijent.grad" class="form-control" disabled>
				    	</div>
				    	<div class="col">
				    		<h6>Država:</h6>
							<input type="text" v-model="pacijent.drzava" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<h6>Broj telefona:</h6>
							<input type="text" v-model="pacijent.brojTelefona" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
							<h6>Jedinstveni broj osiguranika - JBO</h6>
							<input type="text" v-model="pacijent.jbo" class="form-control" disabled>
				    	</div>
				  	</div>
				  	<div>
				  		<button v-if="uloga == 'ROLE_LEKAR'" class="btn btn-lg btn-primary" v-on:click="zapocni">Započni pregled</button>
						<button class="btn btn-lg btn-info" @click="dijalog = true" v-on:click="dobaviZdravstveniKarton" :disabled="!nijeObavioNikadPregled && this.uloga == 'ROLE_LEKAR'">Zdravstveni karton</button>
						<button class="btn btn-lg btn-secondary" style="float: right;" v-on:click="nazad">Nazad</button>
				  	</div>
				</div>
			</div>
			
			<v-dialog v-model="dijalog">
				<v-card>
					<div class="naviga">
						<div class="container d-flex justify-content-center">
							<div class="card mt-5" style="width: 70rem;">
								<h4 class="card-header">Zdravstveni karton</h4>
								<div class="card-body">
									<ul class="list-group">
									  	<li class="list-group-item">
									  		<div class="d-flex w-20 justify-content-between">
										  		<h6>Ime:</h6>
										  		<p class="mb-0">{{ this.pacijent.ime }}</p>
										  	</div>
										  	<div class="d-flex w-20 justify-content-between">
										  		<h6>Prezime:</h6>
										  		<p class="mb-0">{{ this.pacijent.prezime }}</p>
										  	</div>
									  	</li>
									  	<li class="list-group-item">
									  		<div class="d-flex w-20 justify-content-between">
										  		<h6>Visina:</h6>
										  		<p class="mb-0">{{ this.zdravstveniKarton.visina }}</p>
										  	</div>
										  	<div class="d-flex w-20 justify-content-between">
										  		<h6>Tezina:</h6>
										  		<p class="mb-0">{{ this.zdravstveniKarton.tezina }}</p>
										  	</div>
									  	</li>
									  	<li class="list-group-item">
									  		<div class="d-flex w-20 justify-content-between">
									  			<h6>Krvna grupa:</h6>
									  			<p class="mb-0">{{ this.zdravstveniKarton.krvnaGrupa }}</p>
									  		</div>
									  		<div class="d-flex w-20 justify-content-between">
									  			<h6>Dioptrija:</h6>
									  			<p class="mb-0">{{ this.zdravstveniKarton.dioptrija }}</p>
									  		</div>
									  	</li>
									  	<li class="list-group-item">
									  		<div class="w-20 justify-content-between">
									    		<h6>Izveštaji:</h6>
												<table v-if="imaBolesti" class="table table-hover table-striped mt-2">
												  	<thead class="thead-light">
												    	<tr>
												    		<th scope="col" width="33%">Tip pregleda</th>
													      	<th scope="col" width="33%">Lekar</th>
													      	<th scope="col" width="34%">Datum</th>
												    	</tr>
												  	</thead>
												  	<tbody>
												  		<tr v-for="iz in zdravstveniKarton.bolesti" data-toggle="modal" v-on:click="izaberiIzvestaj(iz)">
												    		<th class="h6" scope="col" width="33%">{{ iz.pregled.tipPregleda.naziv }}</th>
												    		<th class="h6" scope="col" width="33%">{{ iz.pregled.lekar.ime + " " + iz.pregled.lekar.prezime }}</th>
												    		<th class="h6" scope="col" width="34%">{{ urediDatum(iz.pregled.vreme2) }}</th>
												    	</tr>
												  	</tbody>
												</table>
											</div>
								  		</li>
									</ul>
									<button v-if="uloga == 'ROLE_MED_SESTRA'" class="m-2 btn btn-lg btn-primary" data-toggle="modal" data-target="#izmenaKartona">Izmeni podatke</button>
								</div>
							</div>
						</div>	
					</div>	
				</v-card>		
			</v-dialog>
			
			<div class="modal fade" id="izmenaKartona" tabindex="-1" role="dialog">
				<div class="modal-dialog modal-lg" role="document">
			    	<div class="modal-content">
			      		<div class="modal-header">
			        		<h5 class="modal-title">Zdravstveni karton</h5>
			        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
			      		</div>
			      		<div class="modal-body">
			        		<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovanoKarton }" novalidate @submit.prevent="izmeniKarton" id="forma-izmeni-karton">
							  	<div class="form-row">
							    	<div class="col">
							    	 	<label for="ime" class="mt-1">Ime</label>
										<input type="text" v-model="pacijent.ime" class="form-control" id="ime" placeholder="Ime" disabled required>
									</div>
							    	<div class="col">
							    	 	<label for="prezime" class="mt-1">Prezime</label>
										<input type="text" v-model="pacijent.prezime" class="form-control" id="prezime" placeholder="Prezime" disabled required>
									</div>
							  	</div>
							  	<div class="form-row">
							  		<div class="col">
							    	 	<label for="visina">Visina</label>
										<input type="number" v-model="noviZdravstveniKarton.visina" min="0" step="1" class="form-control" id="visina" placeholder="Visina">
										<div class="invalid-feedback" id="izmenaInvalid">Unesite ispravnu visinu.</div>
									</div>
									<div class="col">
							    	 	<label for="tezina">Težina</label>
										<input type="number" v-model="noviZdravstveniKarton.tezina" min="0" step="1" class="form-control" id="tezina" placeholder="Težina">
										<div class="invalid-feedback" id="izmenaInvalid">Unesite ispravnu težinu.</div>
									</div>
							  	</div>
							  	<div class="form-row">
							  		<div class="col">
							  			<label for="krgr">Krvna grupa</label>
							    	 	<select class="custom-select mt-0" v-model="noviZdravstveniKarton.krvnaGrupa" id="krgr">
									    	<option value="A+">A+</option>
									    	<option value="A-">A-</option>
									    	<option value="B+">B+</option>
									    	<option value="B-">B-</option>
									    	<option value="AB+">AB+</option>
									    	<option value="AB-">AB-</option>
									    	<option value="0+">0+</option>
									    	<option value="0-">0-</option>
									  	</select>
									</div>
									<div class="col">
							    	 	<label for="diop">Dioptrija</label>
										<input type="number" v-model="noviZdravstveniKarton.dioptrija" min="0" step=".1" class="form-control" id="diop" placeholder="Dioptrija">
										<div class="invalid-feedback" id="izmenaInvalid">Unesite dioptriju.</div>
									</div>
							  	</div>
							  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
							  		Sačuvaj izmene
							  	</button>
							</form>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			      		</div>
			    	</div>
				</div>
			</div>
			
			<div class="modal fade" id="prikaziIzvestajBezIzmene" tabindex="-1" role="dialog">
				<div class="modal-dialog modal-lg" role="document">
			    	<div class="modal-content">
			      		<div class="modal-header">
			        		<h5 class="modal-title">Izveštaj o pregledu</h5>
			        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
			      		</div>
			      		<div class="modal-body">
							<div class="d-flex">
								<p class="h5 m-2">Informacije: {{ izabraniIzvestaj.opis }}</p>
							</div>
							<div class="d-flex">
								<p class="h5 m-2">Dijagnoza: {{ izabraniIzvestaj.dijagnoza.naziv }}</p>
							</div>
							<p class="h3 mt-4 ml-2 font-weight-normal">Recepti</p>
							<table class="table table-hover table-striped">
							  	<thead class="thead-light">
							    	<tr>
								      	<th scope="col" width="50%">Šifra</th>
								      	<th scope="col" width="50%">Naziv leka</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  		<tr v-for="rec in izabraniIzvestaj.recepti" data-toggle="modal" data-target="#" v-on:click="">
								      	<td width="50%">{{ rec.lek.sifra }}</td>
								      	<td width="50%">{{ rec.lek.naziv }}</td>
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
			
			<div class="modal fade" id="prikaziIzvestajSaIzmenom" tabindex="-1" role="dialog">
				<div class="modal-dialog modal-lg" role="document">
			    	<div class="modal-content">
			      		<div class="modal-header">
			        		<h5 class="modal-title">Izveštaj o pregledu</h5>
			        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
			      		</div>
			      		<div class="modal-body">
							<div class="d-flex">
								<p class="h5">Pacijent: {{ pacijent.ime + " " + pacijent.prezime }}</p>
							</div>
						    
						    <form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovanoIzvestaj }" novalidate @submit.prevent="izmeniIzvestaj" id="forma-izmeni-izvestaj">
							    <div class="form-row">
							    	<div class="col">
										<label for="opis">Opis</label>
										<textarea v-model="noviIzvestaj.opis" class="form-control" id="opis" placeholder="Informacije o pregledu" rows="3" required></textarea>
										<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli informacije o pregledu.</div>
							    	</div>
							  	</div>
							  	<div class="form-row">
							    	<div class="col">
							    		<label for="dij" class="mt-1">Dijagnoza</label>
										<select class="custom-select mt-0" v-model="noviIzvestaj.dijagnoza" id="dij" required>
									    	<option v-for="d in sifrarnikDijagnoza.stavke" :value="d">
												{{ d.sifra + " - " + d.naziv }}
									    	</option>
										</select>
										<div class="invalid-feedback" id="dodavanjeInvalid">Niste izabrali dijagnozu.</div>
							    	</div>
							  	</div>
							</form>
		
							<p class="h3 mt-4 ml-2 font-weight-normal">Recepti</p>
							<table class="table table-hover table-striped">
							  	<thead class="thead-light">
							    	<tr>
								      	<th scope="col" width="30%">Šifra</th>
								      	<th scope="col" width="60%">Naziv leka</th>
								      	<th scope="col" width="10%"></th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  		<tr v-for="rec in noviIzvestaj.recepti" data-toggle="modal" data-target="#" v-on:click="">
								      	<td width="30%">{{ rec.lek.sifra }}</td>
								      	<td width="60%">{{ rec.lek.naziv }}</td>
								      	<td width="10%"><button class="btn btn-danger btn-sm" v-on:click="ukloniLek(rec)" id="uklon">Ukloni</button></td>
							    	</tr>
							  	</tbody>
							</table>
							<div class="input-group mb-4">
							  	<select class="custom-select form-control mt-0" id="lekoviSel">
							    	<option v-for="l in sifrarnikLekova.stavke" :value="l">
										{{ l.sifra + " - " + l.naziv }}
							    	</option>
								</select>
								<div class="input-group-prepend">
									<button class="btn btn-primary ml-2" style="float: left;" v-on:click="dodajRecept">Dodaj lek</button>
							  	</div>
							</div>
					  		<button class="btn btn-lg btn-primary" type="submit" v-on:click="zavrsiIzvestaj">Sačuvaj izmene</button>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			      		</div>
			    	</div>
				</div>
			</div>
			
		</div>	
	</v-app>
	`,
	methods:{
		izaberiIzvestaj : function (izv) {
			this.izabraniIzvestaj = izv;
			this.noviIzvestaj = JSON.parse(JSON.stringify(this.izabraniIzvestaj));
			if (this.izabraniIzvestaj.pregled.lekar.id == this.korisnik.id) {
				$("#prikaziIzvestajSaIzmenom").modal("show");
			} else {
				$("#prikaziIzvestajBezIzmene").modal("show");
			}
		},
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		nazad:function(){
			localStorage.removeItem("pacijent");
			this.$router.replace({ name: 'pacijenti' });
		},
		dobaviZdravstveniKarton: function(){
			axios
    		.put('/zdravstveniKartoni/dobaviKartonPacijenta', this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.zdravstveniKarton = response.data;
            	this.noviZdravstveniKarton = JSON.parse(JSON.stringify(this.zdravstveniKarton));
            	var nema = this.zdravstveniKarton.bolesti === undefined || this.zdravstveniKarton.bolesti.length == 0;
    			this.imaBolesti = !nema;
    			if (this.uloga == "ROLE_MED_SESTRA") {
    				this.dijalog = false;
    				$("#izmenaKartona").modal("show");
    			}
            })
            .catch(function (error) { console.log(error); });
		},
		zapocni : function() {
			axios
			.post('pregledi/mozeZapocetiPregled', { idLekara:this.korisnik.id, idPacijenta:this.pacijent.id }, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {
				if (!response.data) {
					alert("Ne možete započeti pregled. Ne postoji pregled koji je u toku.");
				} else {
					axios
					.post('pregledi/daLiJeZavrsen', { idLekara:this.korisnik.id, idPacijenta:this.pacijent.id }, { headers: { Authorization: 'Bearer ' + this.token }})
					.then(response => {
						if (response.data) {
							alert("Ovaj pregled je završen.");
						} else {
							this.$router.replace({ name: 'zapocniPregled' });
						}
					})
					.catch(function (error) { console.log(error); });
				}
			})
			.catch(function (error) { console.log(error); });
		},
		zavrsiIzvestaj : function () {
			this.submitovano = true;
			if (document.getElementById('forma-izmeni-izvestaj').checkValidity() === true) {
				axios
	    		.post('/izvestaji/izmeniIzvestaj', this.noviIzvestaj, { headers: { Authorization: 'Bearer ' + this.token }} )
	            .then(response => {
	            	toast("Uspešno izmenjen izveštaj");
	            	this.dobaviZdravstveniKarton();
	            })
	    		.catch(function (error) { console.log(error); });
			}
		},
		dodajRecept : function () {
			var selekt = document.getElementById("lekoviSel");
			var lekSifra = selekt.options[selekt.selectedIndex].text.split(" - ")[0];
			var lekic = this.nadjiLek(lekSifra);
			if (!this.sadrzi(lekic)) {
				this.noviIzvestaj.recepti.push({ lek: lekic });
			}
		},
		ukloniLek : function (lek) {
			const indeks = this.noviIzvestaj.recepti.indexOf(lek);
			if (indeks > -1) {
				this.noviIzvestaj.recepti.splice(indeks, 1);
			}
		},
		nadjiLek : function (sifra) {
			for (let stavka of this.sifrarnikLekova.stavke) {
				if (stavka.sifra == sifra) {
					return stavka;
				}
			}
		},
		izmeniKarton : function() {
			this.submitovanoKarton = true;
			if (document.getElementById('forma-izmeni-karton').checkValidity() === true) {
				axios
	    		.put('/zdravstveniKartoni/izmeniKarton', this.noviZdravstveniKarton, { headers: { Authorization: 'Bearer ' + this.token }} )
	            .then(response => {
	            	toast("Uspešno izmenjen zdravstveni karton");
	            })
	    		.catch(function (error) { console.log(error); });
			}
		},
		sadrzi (lekic) {
			for (let l of this.noviIzvestaj.recepti) {
				if (l.lek.id == lekic.id) {
					return true;
				}
			}
			return false;
		},
	},
	mounted(){
		this.token = localStorage.getItem("token");
		this.pacijent = JSON.parse(localStorage.getItem("pacijent"));
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data;
        	axios
    		.put('/korisnici/dobaviUlogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.uloga = response.data;
            	if (this.uloga == "ROLE_LEKAR") {
            		axios
        			.post('pregledi/provjeraPregledaZaPacijentaOdLekara', { idLekara:this.korisnik.id, idPacijenta:this.pacijent.id }, { headers: { Authorization: 'Bearer ' + this.token }})
        			.then(response=>{
        				if (response.data == true) {
        					this.nijeObavioNikadPregled = true;
        				} else
        				{
        					this.nijeObavioNikadPregled = false;
        				}
        			})
        			.catch(function (error) { console.log(error); });
            	}
            })
            .catch(function (error) { console.log(error); });
        })
		.catch(function (error) { console.log(error); });
		axios
		.get('/sifrarnici/ucitajSifrarnikDijagnoza', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.sifrarnikDijagnoza = response.data;
        })
		.catch(function (error) { console.log(error); });
		axios
		.get('/sifrarnici/ucitajSifrarnikLekova', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.sifrarnikLekova = response.data;
        })
		.catch(function (error) { console.log(error); });
	}
});