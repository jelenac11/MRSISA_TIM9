Vue.component("zapocni-pregled", {
	data : function() {
		return {
			token: "",
			korisnik: {},
			pacijent: {},
			zdravstveniKarton: {},
			noviZdravstveniKarton: {},
			pregled: {},
			izvestaj: {
				opis: "",
				dijagnoza: {},
				recepti: [],
				pregled: {},
			},
			sifrarnikLekova: {},
			sifrarnikDijagnoza: {},
			submitovano: false,
			submitovanoKarton: false,
			dijalog:false,
			e6:1,
			terminPregleda:{
				pacijent:null,
				datumiVreme:null,
				tipPregleda:null,
				trajanje:0,
				lekar:null
			},
			odabranDatum:true,
			termini:[],
			termin:null,
			odabraniTermin:true,
			pretragaTermina:{id:0,datum:0,tipPregleda:'',pacijent:0,klinika:0},
			radioGroup: "operacija"
		} 
	},
	template: 
	`
	<v-app>
		<div class="container d-flex justify-content-center">
			<div class="card mt-5" style="width: 70rem;">
				<h4 class="card-header">Izveštaj o pregledu</h4>
				<div class="card-body">
					
					<div class="d-flex">
						<p class="h5">Pacijent: {{ pacijent.ime + " " + pacijent.prezime }}</p>
					</div>
				    
				    <form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="zavrsiIzvestaj" id="forma-zavrsi-izvestaj">
					    <div class="form-row">
					    	<div class="col">
								<label for="opis">Opis</label>
								<textarea v-model="izvestaj.opis" class="form-control" id="opis" placeholder="Informacije o pregledu" rows="3" required></textarea>
								<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli informacije o pregledu.</div>
					    	</div>
					  	</div>
					  	<div class="form-row">
					    	<div class="col">
					    		<label for="dij" class="mt-1">Dijagnoza</label>
								<select class="custom-select mt-0" v-model="izvestaj.dijagnoza" id="dij" required>
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
					  		<tr v-for="rec in izvestaj.recepti" data-toggle="modal" data-target="#" v-on:click="">
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

				  	<div>
				  		<button class="btn btn-lg btn-primary" style="color:white" type="submit" v-on:click="zavrsiIzvestaj">Završi pregled</button>
						<button class="btn btn-lg btn-info" data-toggle="modal" data-target="#izmenaKartona" v-on:click="dobaviZdravstveniKarton">Zdravstveni karton</button>
						<button class="btn btn-lg btn-info" @click="dijalog = true" v-on:click="noviTermin">Zakaži novi termin</button>
						<button class="btn btn-lg btn-secondary" style="float: right;" v-on:click="nazad">Nazad</button>
				  	</div>
				</div>
			</div>
			
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
							  	<button style="color:white" class="btn btn-lg btn-primary btn-block mt-4" type="submit">
							  		Sačuvaj izmene
							  	</button>
							</form>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" style="color:white" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			      		</div>
			    	</div>
				</div>
			</div>
			
			
			<v-dialog width="500" v-model="dijalog">
				<v-stepper v-model="e6">
					<v-stepper-step :complete="e6 > 1" step="1">
				        Odabir datuma
				    </v-stepper-step>
				    	<v-stepper-content step="1">
							<div class="form-row mb-3">
								<div class="col">
									<label for="datum" class="mt-1">Datum termina</label>
									<input type="date" v-model="terminPregleda.datumiVreme" class="form-control" id="datum" v-on:change="promjenaDatuma" v-bind:class="{ 'is-invalid':!odabranDatum}" required>
									<div class="invalid-feedback" id="dodavanjeInvalid">Odabrani datum je nevalidan.</div>
								</div>
							</div>
							<div class="form-row mb-3">
								<div class="col">
									<label for="tipTermina" class="mt-1">Tip termina</label>
									<v-radio-group v-model="radioGroup">
										<v-radio label="Operacija" value="operacija"></v-radio>
										<v-radio label="Pregled" value="pregled"></v-radio>
									</v-radio-group>
									<div class="invalid-feedback" id="dodavanjeInvalid">Odabrani datum je nevalidan.</div>
								</div>
							</div>
	        		 		<v-btn class="primary" v-on:click="next">Next</v-btn>
						</v-stepper-content>
						<v-divider></v-divider>
						
						
						<v-stepper-step :complete="e6 > 2" step="2">
							Odabir termina
						</v-stepper-step>
						
						
						<v-stepper-content step="2">				
							<div class="form-row">
								<div class="col">
									<label for="termin" class="mt-1">Termin</label>
									<select class="custom-select mt-0" v-model="termin" id="termin" v-bind:class="{ 'is-invalid':!odabraniTermin}" required>
										<option v-for="termin in termini" :value="termin">
											{{ urediDatum(termin) }}
										</option>
									</select>
									<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali termin.</div>
								</div>
							</div>
							
				        	<v-btn v-on:click="zakazi" class="primary">Zakaži</v-btn>
				        	<v-btn v-on:click="prev">Nazad</v-btn>
				        </v-stepper-content>
				      </v-stepper>
					</v-dialog>
					
		</div>	
	</v-app>
	`,
	methods: {
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
		zavrsiIzvestaj : function () {
			this.submitovano = true;
			if (document.getElementById('forma-zavrsi-izvestaj').checkValidity() === true) {
				axios
	    		.post('/izvestaji/dodajIzvestaj', this.izvestaj, { headers: { Authorization: 'Bearer ' + this.token }} )
	            .then(response => {
	            	this.$router.replace({ name: 'profilPacijenta' });
	            })
	    		.catch(function (error) { console.log(error); });
			}
		},
		nazad : function () {
			this.$router.replace({ name: 'profilPacijenta' });
		},
		dodajRecept : function () {
			var selekt = document.getElementById("lekoviSel");
			var lekSifra = selekt.options[selekt.selectedIndex].text.split(" - ")[0];
			var lekic = this.nadjiLek(lekSifra);
			if (!this.sadrzi(lekic)) {
				this.izvestaj.recepti.push({ lek: lekic });
			}
		},
		ukloniLek : function (lek) {
			const indeks = this.izvestaj.recepti.indexOf(lek);
			if (indeks > -1) {
				this.izvestaj.recepti.splice(indeks, 1);
			}
		},
		nadjiLek (sifra) {
			for (let stavka of this.sifrarnikLekova.stavke) {
				if (stavka.sifra == sifra) {
					return stavka;
				}
			}
		},
		sadrzi (lekic) {
			for (let l of this.izvestaj.recepti) {
				if (l.lek.id == lekic.id) {
					return true;
				}
			}
			return false;
		},
		dobaviZdravstveniKarton : function () {
			axios
    		.put('/zdravstveniKartoni/dobaviKartonPacijenta', this.pacijent, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.zdravstveniKarton = response.data;
            	this.noviZdravstveniKarton = JSON.parse(JSON.stringify(this.zdravstveniKarton));
            })
            .catch(function (error) { console.log(error); });
		},
		promjenaDatuma:function(){
			if(this.terminPregleda.datumiVreme==0 || new Date(this.terminPregleda.datumiVreme).getTime()<=(new Date().getTime())){
				this.odabranDatum=false;
				return false;
			}
			else{
				
			this.odabranDatum=true;
			return true;
			}
		},
		zakazi: function(){
			this.promjenaDatuma();
			if(this.termin==null){
				this.odabraniTermin=false;
			}
			else{
				this.odabraniTermin=true;
			}
			if( !this.odabranDatum || !this.odabraniTermin){
				return false;
			}
			let termin=JSON.parse(JSON.stringify(this.terminPregleda))
			termin.datumiVreme=this.termin
			if(this.radioGroup==="pregled"){
				axios
				.post('pregledi/zakaziNoviTerminLekar',termin, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response=>{
					if(response.data==true){
						toast("Novi termin pregleda je rezervisan.");
						this.dijalog=false;
					}
				})
				.catch(function (error) { console.log(error); });
			}
			else{
				axios
				.post('operacije/zakaziNoviTerminLekar',termin, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response=>{
					if(response.data==true){
						toast("Novi termin operacije je rezervisan.");
						this.dijalog=false;
					}
				})
				.catch(function (error) { console.log(error); });
			}
		},
		prev: function(){
			this.termin=null;
			this.odabraniTermin=true;
			this.e6=1;
		},
		next: function(){
			this.promjenaDatuma();
			if(!this.odabranDatum){
				return false;
			}
			this.pretragaTermina.id=this.terminPregleda.lekar;
			this.pretragaTermina.datum=Date.parse(this.terminPregleda.datumiVreme) - 7200000;
			axios
			.put('lekari/proveriGodisnji',this.pretragaTermina, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				if(response.data==true){
					axios
					.put('lekari/vratiSlobodneTermine',this.pretragaTermina, { headers: { Authorization: 'Bearer ' + this.token }})
					.then(response=>{
						this.termini=response.data;
						if(this.termini.length==0){
							toast("Nema slobodnih termina za izabrani datum.")
						}
						else{
							this.e6=2;
						}
					})
					.catch(function (error) { console.log(error); });
				}
				else{
					toast("Za izabrani datum ste na godišnjem odmoru.");
				}
			})
			.catch(function (error) { console.log(error); });
		},
		noviTermin: function(){
			this.terminPregleda.pacijent=this.pacijent.id;
			this.terminPregleda.lekar=this.korisnik.id;
			this.terminPregleda.trajanje=3600000;
			this.terminPregleda.datumiVreme=0;
			this.terminPregleda.tipPregleda=this.pregled.tipPregleda;
			this.termin=null;
			this.odabraniTermin=true;
			this.odabranDatum=true;
			this.e6=1;
		},
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
	},
	mounted () {
		this.token = localStorage.getItem("token");
		this.pacijent = JSON.parse(localStorage.getItem("pacijent"));
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik = response.data;
        	axios
    		.post('/pregledi/dobaviPregled', { idLekara: this.korisnik.id, idPacijenta: this.pacijent.id }, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => {
            	this.pregled = response.data;
            	this.izvestaj.pregled = this.pregled;
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