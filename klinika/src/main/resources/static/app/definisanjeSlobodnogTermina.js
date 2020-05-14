Vue.component("definisanje-slobodnog-termina", {
	data : function() {
		return {
			
			slobodniPregledi:[],
			currentTab: 0,
			token: "",
			terminPregleda:{
				idAdmina:0,
				datumiVreme:null,
				tipPregleda:null,
				trajanje:0,
				lekar:null,
				sala:null
			},
			tipoviPregleda:[],
			korisnik:{},
			sale:[],
			lekari:[],
			odabranDatum:true,
			odabraniTipPregleda:true,
			odabraniLekar:true,
			odabranaSala:true,
			odabraniTermin:true,
			odabranoTrajanje:true,
			dijalog: false,
			e6:1,
			activeTab:"2",
			termini:[],
			termin:null,
			pretragaTermina:{id:0,datum:0,tipPregleda:'',pacijent:0,klinika:0}
		} 
	},
	template: `
	<v-app>
	<div data-app>
	<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga">			    
			<v-tabs v-model="activeTab" centered>
		      <v-tab href="#1" v-on:click="promijeniTab(1)">
				<router-link :to="{ name: 'zakazaniPregledi' }">Zakazani pregledi</router-link>
		      </v-tab>
		      <v-tab href="#2" v-on:click="promijeniTab(2)">
				<router-link :to="{ name: 'definisanjeSlobodnogTermina' }">Slobodni termini</router-link>
		      </v-tab>
		      <v-tab href="#3" v-on:click="promijeniTab(3)">
				<router-link :to="{ name: 'naCekanjuTermini' }">Pregledi bez sale</router-link>
		      </v-tab>
		      <v-tab href="#4" v-on:click="promijeniTab(4)">
				<router-link :to="{ name: 'naCekanjuOperacije' }">Operacije bez sale</router-link>
		      </v-tab>
		    </v-tabs>
		</div>
		<div class="naviga tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="25%">Lekar</th>
				      	<th scope="col" width="10%">Vreme</th>
				      	<th scope="col" width="15%">Sala</th>
				      	<th scope="col" width="15%">Tip pregleda</th>
				      	<th scope="col" width="10%">Trajanje</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="pregled in slobodniPregledi">
				      	<td width="25%">{{ pregled.lekar.ime }} {{ pregled.lekar.prezime }}</td>
				      	<td width="10%">{{ formatVreme(pregled.vreme) }}</td>
				      	<td width="15%">{{ pregled.sala.naziv }}</td>
				      	<td width="15%">{{ pregled.tipPregleda.naziv }}</td>
				      	<td width="10%">{{ formatTrajanje(pregled.trajanje) }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<v-btn @click="dijalog = true" :block="true" class="primary" v-on:click="reset">
        		Kreiraj novi slobodan termin
			</v-btn>
		</div>
		
		<v-dialog v-model="dijalog">
		
		
			<v-stepper v-model="e6">
				<v-stepper-step :complete="e6 > 1" step="1">
			        Odabir lekara i datuma pregleda
			    </v-stepper-step>
			    	<v-stepper-content step="1">
			    	<div class="form-row">
							<div class="col">
								<label for="lekar" class="mt-1">Lekar</label>
								<select class="custom-select mt-0" v-model="terminPregleda.lekar" v-bind:class="{ 'is-invalid':!odabraniLekar}" required>
									<option v-for="lekar in lekari" :value="lekar">
										{{ lekar.ime}} {{lekar.prezime }}
									</option>
								</select>
								<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali lekara.</div>
							</div>
						</div>
						<div class="form-row mb-3">
							<div class="col">
								<label for="datum" class="mt-1">Datum pregleda</label>
								<input type="date" v-model="terminPregleda.datumiVreme" class="form-control" id="datum" v-on:change="promjenaDatuma" v-bind:class="{ 'is-invalid':!odabranDatum}" required>
								<div class="invalid-feedback" id="dodavanjeInvalid">Odabrani datum je nevalidan.</div>
							</div>
						</div>
        		 		<v-btn class="primary" v-on:click="next">Next</v-btn>
					</v-stepper-content>
					<v-divider></v-divider>
					
					
					<v-stepper-step :complete="e6 > 2" step="2">
						Odabir termina pregleda i tipa pregleda
					</v-stepper-step>
					
					
					<v-stepper-content step="2">
						<div class="form-row">
									<div class="col">
										<label for="tipPregleda" class="mt-1">Tip pregleda</label>
										<select class="custom-select mt-0" v-model="terminPregleda.tipPregleda" id="tipPregleda" v-bind:class="{ 'is-invalid':!odabraniTipPregleda}" required>
											<option v-for="tip in tipoviPregleda" :value="tip">
												{{ tip.naziv }}
											</option>
										</select>
										<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali tip pregleda.</div>
									</div>
						</div>
						
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
						
			        	<v-btn v-on:click="next2" class="primary">Next</v-btn>
			        	<v-btn v-on:click="prev">Nazad</v-btn>
			        </v-stepper-content>
			        
			        <v-stepper-step :complete="e6 > 3" step="3">
						Odabir sale
					</v-stepper-step>
					
			      <v-stepper-content step="3">
						<div class="form-row">
							<div class="col">
								<label for="sala" class="mt-1">Sala</label>
								<select class="custom-select mt-0" v-model="terminPregleda.sala" v-bind:class="{ 'is-invalid':!odabranaSala}" required>
									<option v-for="sala in sale" :value="sala">
										{{ sala.naziv }}
									</option>
								</select>
								<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali salu.</div>
							</div>
						</div>
			        	<v-btn v-on:click="finish" class="primary">Finish</v-btn>
			        	<v-btn v-on:click="prev2">Nazad</v-btn>
			      	</v-stepper-content>
			      </v-stepper>
				</v-dialog>
	</div>
	</v-app>
	`,
	created: function(){
		this.token = localStorage.getItem("token");
	},
	mounted: function(){
        axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data;
        	this.terminPregleda.idAdmina=this.korisnik.id;
        	axios
            .get('/tipoviPregleda/ucitajSvePoIdKlinike/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.tipoviPregleda = response.data))
            .catch(function (error) { console.log(error); });
        	
        	axios
            .get('/pregledi/ucitajSveSlobodnePreglede/'+this.korisnik.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.slobodniPregledi = response.data))
            .catch(function (error) { console.log(error); });
        	
        })
        .catch(function (error) { console.log(error); });
	},
	methods: {
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		formatVreme:function(datum){
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            datum=datum+" "+date.toLocaleTimeString()
            return datum
        },
		next:function() {
			  if (!this.validateForm1()) return false;
			  this.dobaviPodatke();

		},
		next2:function(){
			 if (!this.validateForm2()) return false;
			 	this.dobaviPodatke2();
		},
		finish:function(){
			  if (!this.validateForm3()) return false;
			  let termin=JSON.parse(JSON.stringify(this.terminPregleda))
			  termin.datumiVreme=this.termin
			  termin.trajanje=3600000
			  axios
			  .post("/pregledi/dodajSlobodanTerminZaPregled",termin, { headers: { Authorization: 'Bearer ' + this.token }})
			  .then(response=>{
				  if(response.data==true){
					  axios
			            .get('/pregledi/ucitajSveSlobodnePreglede/'+this.korisnik.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
			            .then(response => {
			            	this.slobodniPregledi = response.data;
			            	this.dijalog=false;
			            })
			            .catch(function (error) { console.log(error); });
				  }
				  else{
					  toast("Neuspesno kreiranje termina za pregled");
					  return;
				  }
			  })
			  .catch(error => {
					console.log(error);
					toast("Neuspesno kreiranje termina za pregled");
					this.uspesnoDodavanje = false;
				});
		},
		prev: function(){
			this.termin=null;
			this.terminPregleda.tipPregleda=null;
			this.e6=1;
		},
		prev2:function(){
			this.terminPregleda.sala=null;
			this.e6=2;
		},
		reset: function(){
			this.termin=null;
			this.terminPregleda={
				idAdmina:this.terminPregleda.idAdmina,
				datumiVreme:0,
				tipPregleda:null,
				trajanje:0,
				lekar:null,
				sala:null
			};
			axios
			.get("/lekari/ucitajSveLekareKlinikeByAdmin/"+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				this.lekari=response.data;
			})
			.catch(function (error) { console.log(error); });
			this.e6=1;
		},
		validateForm1:function(){
			this.promjenaDatuma();
			if(this.terminPregleda.lekar==null){
				this.odabraniLekar=false;
			}
			else{
				this.odabraniLekar=true;
			}
			
			if(this.odabraniLekar && this.odabranDatum){
				return true;
			}
			else{
				return false;
			}
			
		},
		validateForm2:function(){
			this.promjenaTipaPregleda();
			this.promjenaDatuma();
			if(this.termin==null){
				this.odabraniTermin=false;
			}
			else{
				this.odabraniTermin=true;
			}
			if(this.odabraniTipPregleda && this.odabranDatum && this.odabraniTermin && this.odabraniLekar){
				return true;
			}
			else{
				return false;
			}	
		},
		validateForm3:function(){
			this.promjenaTipaPregleda();
			this.promjenaDatuma();
			if(this.termin==null){
				this.odabraniTermin=false;
			}
			else{
				this.odabraniTermin=true;
			}
			if(this.terminPregleda.sala==null){
				this.odabranaSala=false;
			}
			else{
				this.odabranaSala=true;
			}
			if(this.odabraniTipPregleda && this.odabranDatum && this.odabraniTermin && this.odabraniLekar && this.odabranaSala){
				return true;
			}
			else{
				return false;
			}	
		},
		promjenaTipaPregleda:function(){
			if(this.terminPregleda.tipPregleda==null){
				this.odabraniTipPregleda=false;
				return false;
			}
			else{
				this.odabraniTipPregleda=true;
				return true;
			}
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
		promjenaTrajanja: function(){
			if(this.terminPregleda.trajanje>0 && this.terminPregleda.trajanje<=120){
				this.odabranoTrajanje=true;
				return true;
			}
			else{
				this.odabranoTrajanje=false;
				return false;
			}
		},
		dobaviPodatke: function(){
			let termin=JSON.parse(JSON.stringify(this.terminPregleda))
			termin.datumiVreme=Date.parse(termin.datumiVreme)
			axios
			.get('/lekari/dobaviTipovePregledaZaLekara/'+this.terminPregleda.lekar.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				this.tipoviPregleda=response.data;
				if(this.tipoviPregleda.length==0){
					toast("Lekar nije specijalizovan ni za jedan tip pregleda.")
					return;
				}
				this.pretragaTermina.id=this.terminPregleda.lekar.id
				this.pretragaTermina.datum=termin.datumiVreme
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
			})
			.catch(function (error) { console.log(error); });
			
		},
		dobaviPodatke2: function(){
			let termin=JSON.parse(JSON.stringify(this.terminPregleda))
			termin.datumiVreme=this.termin
				axios
				.post("/sale/dobaviSlobodneSaleZaPregled",termin, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response=>{
					this.sale=response.data;
					  if(this.sale.length==0){
						  toast("Nema slobodnih sala za izabrani termin.");
						  return false;
					  }
					  this.e6=3;
					})
				.catch(function (error) { console.log(error); });
			
		},
		formatTrajanje: function(trajanje){
			return parseInt(trajanje)/60000+ " minuta"
		},
		promijeniTab: function(a){
			if(a==1){
				this.$router.replace({ name: 'zakazaniPregledi' });
			}
			else if(a==2){
				this.$router.replace({ name: 'definisanjeSlobodnogTermina' });
			}
			else if (a==3){
				this.$router.replace({ name: 'naCekanjuTermini' });
			} 
			else {
				this.$router.replace({ name: 'naCekanjuOperacije' });
			}
		}
	}
});