Vue.component("na-cekanju-operacije", {
	data : function() {
		return {
			token: "",
			naCekanjuOperacije: [],
			dialog: false,
			dialog2: false,
			dialog3: false,
			dialog4: false,
			sale: [],
			moguciLekari: [],
			izabraniLekari: [],
			noveSale: [],
			search: '',
			searchBroj: '',
			brojManje: '',
			brojVece: '',
			zaglavljeLekari: [
				{
					text: 'Ime',
					align: 'start',
					sortable: true,
					value: 'ime',
		        },
		        { 
		        	text: 'Prezime',
		        	sortable: true,
		        	value: 'prezime',
		        },
	        ],
		    terminOperacije: {
				idAdmina: 0,
				datumiVreme: 0,
				trajanje: 3600000,
				lekari: [],
				sala: null,
				operacijaId: null,
			},
			noviTerminOperacije: {
				idAdmina: 0,
				datumiVreme: 0,
				trajanje: 3600000,
				lekari: [],
				sala: null,
				operacijaId: null,
			},
			noviPocetniLekar: {},
			ulogovan: {},
			dodatiLekari: [],
			selected: [],
			kreirana: 0,
			date: new Date().toISOString().substr(0, 10),
		    menu: false,
		    menu2: false,
		    time: null,
		    lekari: null,
		    lekariOp: null,
		    odabranDatum: true,
		    odabraniLekar: true,
		    e6: 1,
		    type: 'month',
		    types: ['month', 'week', '4day', 'day'],
		    weekday: [0, 1, 2, 3, 4, 5, 6],
		    value: '',
		    events: [],
		    activeTab: '5',
		    dijalogGreska: false,
		} 
	},
	template: `
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
			<div data-app>
				<div class="naviga">			    
					<v-tabs v-model="activeTab" centered>
				      <v-tab href="#1" v-on:click="promijeniTab(1)">
						<router-link :to="{ name: 'zakazaniPregledi' }">Zakazani pregledi</router-link>
				      </v-tab>
				      <v-tab href="#2" v-on:click="promijeniTab(2)">
						<router-link :to="{ name: 'zakazaneOperacije' }">Zakazane operacije</router-link>
				      </v-tab>
				      <v-tab href="#3" v-on:click="promijeniTab(3)">
						<router-link :to="{ name: 'definisanjeSlobodnogTermina' }">Predefinisani termini</router-link>
				      </v-tab>
				      <v-tab href="#4" v-on:click="promijeniTab(4)">
						<router-link :to="{ name: 'naCekanjuTermini' }">Pregledi bez sale</router-link>
				      </v-tab>
				      <v-tab href="#5" v-on:click="promijeniTab(5)">
						<router-link :to="{ name: 'naCekanjuOperacije' }">Operacije bez sale</router-link>
				      </v-tab>
				    </v-tabs>
				</div>
				<div class="naviga tab-content">
					<table class="table table-hover table-striped">
					  	<thead class="thead-light">
					    	<tr>
						      	<th scope="col" width="27%">Pacijent</th>
						      	<th scope="col" width="27%">Lekari</th>
						      	<th scope="col" width="26%">Vreme</th>
						      	<th scope="col" width="20%">Trajanje</th>
					    	</tr>
					  	</thead>
					  	<tbody>
					  		<tr v-for="op in naCekanjuOperacije" @click="dialog = true" v-on:click="izabranaOperacija(op)">
						      	<td width="27%">{{ op.pacijent.ime }} {{ op.pacijent.prezime }}</td>
						      	<td width="27%">{{ ispisiLekare(op.lekari) }}</td>
						      	<td width="26%">{{ formatVreme(op.vreme) }}</td>
						      	<td width="20%">60 minuta</td>
					    	</tr>
					  	</tbody>
					</table>
				</div>
				
				<v-dialog width="700" v-model="dialog">
						
					<v-card>
						<v-card-title>
		        			Sale
		        		<v-spacer></v-spacer>
		        		<v-text-field
							v-model="search"
							append-icon="mdi-magnify"
							label="Pretraga po imenu..."
							single-line
							hide-details
		        		></v-text-field>
		        		<v-spacer></v-spacer>
		        		<v-text-field
							v-model="searchBroj"
			          		append-icon="mdi-magnify"
				          	label="Pretraga po broju..."
				          	single-line
				          	hide-details
		        		></v-text-field>
						</v-card-title>
						
						<v-data-table
							v-model="selected"
							:headers="zaglavlje"
							:items="sale"
							:single-select="true"
							show-select
							no-data-text="Nema dostupnih sala"
							no-results-text="Nema rezultata pretrage"
							item-key="naziv"
							class="elevation-1"
						>
							<template v-slot:body.append>
			          			<tr>
			          				<td>Broj</td>
									<td>
			              				<v-text-field v-model="brojManje" type="number" label="Od"></v-text-field>
			            			</td>
			            			<td>
			              				<v-text-field v-model="brojVece" type="number" label="Do"></v-text-field>
			            			</td>
			          			</tr>
			          			<tr>
				          			<td></td>
			          				<td>
			          					<v-btn @click="dialog4 = true" v-on:click="nadjiLekare" :disabled="sale.length==0">
		        							Dodaj lekare
										</v-btn>
									</td>
			          				<td>
			          					<v-btn @click="dialog2 = true" v-on:click="reset" :disabled="sale.length!=0">
		        							Promeni podatke
										</v-btn>
									</td>
			          				<td align="right">
			          					<v-btn v-on:click="dodijeliSalu" :disabled="selected.length==0">
		        							Dodeli salu
										</v-btn>
			          				</td>
			          			</tr>
							</template>
							<template v-slot:item.Kalendar="{ item }">
								<v-btn class="secondary" small v-on:click="dobaviRadniKalendar(item)" @click="dialog3 = true">
			        				Radni kalendar
								</v-btn>
							</template>
						</v-data-table>
					</v-card>
				</v-dialog>
				
				<v-dialog width="500" v-model="dialog2" persistent>
					<v-stepper v-model="e6">
						<v-stepper-step :complete="e6 > 1" step="1">
					        Odabir datuma
					    </v-stepper-step>
				    	<v-stepper-content step="1">
				    		<div class="form-row mb-3">
								<div class="col">
									<label for="datum" class="mt-1">Datum i vreme operacije</label>
									<input type="datetime-local" v-model="noviTerminOperacije.datumiVreme" class="form-control" id="datum" v-on:change="promjenaDatuma" v-bind:class="{ 'is-invalid' : !odabranDatum }" required>
									<div class="invalid-feedback" id="dodavanjeInvalid">Odabrani datum je nevalidan.</div>
								</div>
							</div>
	        		 		<v-btn class="primary" v-on:click="next">Next</v-btn>
	        		 		<v-btn v-on:click="cancel">Izlaz</v-btn>
						</v-stepper-content>
						
						<v-divider></v-divider>
						
						<v-stepper-step :complete="e6 > 2" step="2">
							Odabir lekara
						</v-stepper-step>
						
						<v-stepper-content step="2">
							<div class="form-row">
								<div class="col">
									<label for="lekar" class="mt-1">Lekar</label>
									<select class="custom-select mt-0" v-model="noviPocetniLekar" v-bind:class="{ 'is-invalid':!odabraniLekar}" required>
										<option v-for="lek in lekari" :value="lek">
											{{ lek.ime }} {{ lek.prezime }}
										</option>
									</select>
									<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali lekara.</div>
								</div>
							</div>
				        	<v-btn v-on:click="finish" class="primary">Finish</v-btn>
				        	<v-btn v-on:click="prev">Nazad</v-btn>
				        	<v-btn v-on:click="cancel">Izlaz</v-btn>
				      	</v-stepper-content>
					      	
				    </v-stepper>
				</v-dialog>
						
				<v-dialog width="500" v-model="dialog4">
					<v-card>
						<v-card-title>
		        			Lekari
		        		</v-card-title>
		        		<v-spacer></v-spacer>
		        		<v-data-table
							v-model="izabraniLekari"
							:headers="zaglavljeLekari"
							:items="moguciLekari"
							:single-select="false"
							show-select
							no-data-text="Nema dostupnih lekara"
							no-results-text="Nema rezultata pretrage"
							item-key="ime"
							class="elevation-1"
						>
							<template v-slot:body.append>
			          			<tr>
				          			<td></td>
			          				<td>
			          					<v-btn v-on:click="dodajLekare">
			    							Dodaj lekare
										</v-btn>
									</td>
			          				<td></td>
			          			</tr>
							</template>
						</v-data-table>
					</v-card>
				</v-dialog>
						
				<v-dialog width="1000" v-model="dialog3">
					<div>
				      	<v-sheet
					        tile height="54"
					        color="grey lighten-3"
					        class="d-flex"
				      	>
					        <v-btn
								icon
					          	class="ma-2"
					          	@click="$refs.calendar.prev()"
					        >
					        	<v-icon>mdi-chevron-left</v-icon>
					        </v-btn>
					        <v-select
					        	v-model="type"
					          	:items="types"
					          	dense
					          	outlined
					          	hide-details
					          	class="ma-2"
					          	label="type"
					        ></v-select>
					        <v-spacer></v-spacer>
					        <v-btn
					          	icon
					          	class="ma-2"
					          	@click="$refs.calendar.next()"
					        >
					          	<v-icon>mdi-chevron-right</v-icon>
					        </v-btn>
				      	</v-sheet>
				      	<v-sheet height="600">
				        	<v-calendar
				          		ref="calendar"
				          		v-model="value"
				          		:weekdays="weekday"
				          		:type="type"
				          		:events="events"
	          			  		:event-overlap-threshold="30"
	          			  		:event-color="getEventColor"
				        	></v-calendar>
			      		</v-sheet>
			    	</div>
				</v-dialog>
			</div>
			<v-dialog v-model="dijalogGreska" max-width="300">
		      <v-card>
		        <v-card-title class="headline">Greška</v-card-title>
		        <v-card-text>Neko drugi je upravo zakazao salu ili lekara u istom terminu.</v-card-text>
		        <v-card-actions>
		          <v-spacer></v-spacer>
		          <v-btn color="green darken-1" text @click="dijalogGreska = false">u redu</v-btn>
		        </v-card-actions>
		      </v-card>
		    </v-dialog>
		</v-app>
	</div>
	`,
	methods : {
		formatVreme:function(datum){
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'numeric', year: 'numeric'
            }).replace(/ /g, '-');
            datum=datum+" "+date.toLocaleTimeString()
            return datum
        },
		formatDate (datum) {
        	var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            return datum
		    },
		formatDatumKalendar:function(a){
			let datum=`${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
			return datum;
		},
		izabranaOperacija : function (op) {
			this.lekariOp = op.lekari;
			this.dialog = true;
			this.brojManje = '';
			this.brojVece = '';
			this.selected = [];
			this.terminOperacije.idAdmina = this.ulogovan.id;
			this.terminOperacije.datumiVreme = op.vreme;
			this.terminOperacije.operacijaId = op.id;
			this.terminOperacije.lekari = op.lekari;
			this.terminOperacije.trajanje = 3600000;
			let termin = JSON.parse(JSON.stringify(this.terminOperacije));
			termin.datumiVreme = Date.parse(termin.datumiVreme);
			axios
			.post("/sale/dobaviSlobodneSaleZaOperaciju", termin, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {
				this.sale=response.data;	
			})
			.catch(function (error) { console.log(error); });
		},
		dodajLekare: function() {
			for (let l of this.izabraniLekari) {
				this.terminOperacije.lekari.push(l);
			}
			this.dialog4 = false;
		},
		dodijeliSalu:function(){
			let sala=this.selected[0];
			for(let s of this.sale){
				if(sala.broj==s.broj){
					sala=s;
					break;
				}
			}
			this.terminOperacije.sala=sala;
			let termin=JSON.parse(JSON.stringify(this.terminOperacije))
			termin.datumiVreme=Date.parse(termin.datumiVreme)
			axios
			.post('/operacije/dodijeliSaluOperaciji', termin, { headers: { Authorization: 'Bearer ' + this.token }} )
			.then(response=>{
				if(response.data){
					axios
		            .get('/operacije/ucitajSveOperacijeNaCekanju/'+this.ulogovan.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
		            .then(response => (this.naCekanjuOperacije = response.data))
		            .catch(function (error) { console.log(error); });
					this.dialog=false;
				}
				else{
					axios
		            .get('/operacije/ucitajSveOperacijeNaCekanju/'+this.ulogovan.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
		            .then(response => (this.naCekanjuOperacije = response.data))
		            .catch(function (error) { console.log(error); });
					this.dialog=false;
					this.dijalogGreska = true;
				}
				
			})
			.catch(function (error) { console.log(error); });
		},
		formatTrajanje: function(trajanje){
			return parseInt(trajanje) / 60000 + " minuta"
		},
		next:function() {
			  if (!this.validateForm1()) return false;
			  this.dobaviPodatke();
		},
		finish:function(){
			  if (!this.validateForm2()) return false;
			  this.noviTerminOperacije.lekari = [];
			  this.noviTerminOperacije.lekari.push(this.noviPocetniLekar);
			  this.noviPocetniLekar = null;
			  let termin=JSON.parse(JSON.stringify(this.noviTerminOperacije))
			  termin.datumiVreme=Date.parse(termin.datumiVreme)
			  this.terminOperacije=JSON.parse(JSON.stringify(this.noviTerminOperacije))
			  this.sale=JSON.parse(JSON.stringify(this.noveSale))
			  this.dialog2=false;
		},
		prev: function(){
			 this.noviTerminOperacije.lekari=[];
			 this.e6=1;
		},
		reset: function(){
			this.noviTerminOperacije=JSON.parse(JSON.stringify(this.terminOperacije));
			this.noviTerminOperacije.datumiVreme=0;
			this.noviTerminOperacije.lekari=[];
			this.e6=1;
		},
		validateForm1:function(){
			
			this.promjenaDatuma();
			
			if( this.odabranDatum){
				return true;
			}
			else{
				return false;
			}
			
		},
		validateForm2:function(){
			this.promjenaDatuma();
			
			if(this.noviPocetniLekar==null){
				this.odabraniLekar=false;
			}
			else{
				this.odabraniLekar=true;
			}
			
			
			if( this.odabranDatum && this.odabraniLekar){
				return true;
			}
			else{
				return false;
			}	
		},
		promjenaDatuma:function(){
			if(this.noviTerminOperacije.datumiVreme==0 || new Date(this.noviTerminOperacije.datumiVreme).getTime()<=(new Date().getTime())){
				this.odabranDatum=false;
				return false;
			}
			else{
				
			this.odabranDatum=true;
			return true;
			}
		},
		nadjiLekare: function() {
			let termin=JSON.parse(JSON.stringify(this.terminOperacije));
			termin.datumiVreme=Date.parse(termin.datumiVreme);
			axios
			.post("/lekari/dobaviSlobodneLekareZaOperaciju", termin, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{ 
				this.moguciLekari = response.data;
				for (let lek of this.terminOperacije.lekari) {
					const indeks = this.nadjiIndeks(lek);
					if (indeks > -1) {
					  this.moguciLekari.splice(indeks, 1);
					}
				}
			})
			.catch(function (error) { console.log(error); });
		},
		nadjiIndeks: function (lekar) {
			for (let l of this.moguciLekari) {
				if (l.id == lekar.id) {
					return this.moguciLekari.indexOf(l);
				}
			}
			return -1;
		},
		dobaviPodatke: function(){
			let termin=JSON.parse(JSON.stringify(this.noviTerminOperacije));
			termin.datumiVreme=Date.parse(termin.datumiVreme);
			axios
			.post("/lekari/dobaviSlobodneLekareZaOperaciju", termin, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				this.lekari=response.data;
				axios
				.post("/sale/dobaviSlobodneSaleZaOperaciju", termin, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response=>{
					this.noveSale=response.data;
					if(this.lekari.length==0 && this.noveSale.length==0){
						  toast("Nema slobodnih sala i lekara za izabrani termin.");
						  return false;
					  }
					  if(this.lekari.length==0){
						  toast("Nema slobodnih lekara za izabrani termin.");
						  return false;
					  }
					  
					  if(this.noveSale.length==0){
						  toast("Nema slobodnih sala za izabrani termin.");
						  return false;
					  }
					  this.e6=2;
					})
				.catch(function (error) { console.log(error); });
				})
			.catch(function (error) { console.log(error); });
		},	
		cancel: function(){
			this.dialog2=false;
		},
		dobaviRadniKalendar:function(item){
			axios.
			get('sale/dobaviRadniKalendar/'+item.id,{ headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				let events=response.data
				const kalendar=[]
				for(let a of events){
					kalendar.push({
				          name: a.naziv,
				          start: this.formatDatumKalendar(new Date(a.start)),
				          end: this.formatDatumKalendar(new Date(a.end)),
				          color: a.naziv=="Operacija"?"green":"blue",
				        })
				}
				this.events=kalendar;
			})
			.catch(function (error) { console.log(error); });
		},
		getEventColor (event) {
		      return event.color
		},
		promijeniTab: function(a){
			if(a==1){
				this.$router.replace({ name: 'zakazaniPregledi' });
			}
			else if(a==2){
				this.$router.replace({ name: 'zakazaneOperacije' });
			}
			else if(a==3){
				this.$router.replace({ name: 'definisanjeSlobodnogTermina' });
			}
			else if (a==4){
				this.$router.replace({ name: 'naCekanjuTermini' });
			} 
			else {
				this.$router.replace({ name: 'naCekanjuOperacije' });
			}
		},
		ispisiLekare: function (lekri) {
			var lekString = "";
			for (let l of lekri) {
				lekString += l.ime + " " + l.prezime + ", ";
			}
			return lekString.substring(0, lekString.length - 2);
		}
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
            .get('/operacije/ucitajSveOperacijeNaCekanju/'+this.ulogovan.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.naCekanjuOperacije = response.data; })
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	},
	computed: {
		zaglavlje(){return [
			{text: 'Naziv',
	          align: 'start',
	          sortable: true,
	          value: 'naziv',
	          filter: value => {
	        	  if(!this.search){
	        		  return true
	        	  }
	        	  else{
	        		 
		        	 return value.toString().toLowerCase().includes(this.search.toString().toLowerCase())
	        	  }
	          }
	        },
	        { text: 'Broj',
	          value: 'broj',
	          filter: value => {
	        	  if(!this.searchBroj){
	        		  if (!this.brojManje && !this.brojVece) return true
			            if(!this.brojManje) return  value <= parseInt(this.brojVece)
			            if(!this.brojVece) return value >= parseInt(this.brojManje)
			            return value >= parseInt(this.brojManje) && value <= parseInt(this.brojVece)
	        	  }
	        	  else{
		        	  if(this.searchBroj==value){
				            if (!this.brojManje && !this.brojVece) return true
				            if(!this.brojManje) return  value <= parseInt(this.brojVece)
				            if(!this.brojVece) return value >= parseInt(this.brojManje)
				            return value >= parseInt(this.brojManje) && value <= parseInt(this.brojVece)
		        		}
		        	  else{
		        		  return false;
		        	  }
	        	  }
	          }
	        },
	        {
	        	text: 'Kalendar', 
	        	value: 'Kalendar',
	        	field: 'Kalendar',
	        }	
	        ]
		},
	},
});