Vue.component("poslovanje", {
	data : function() {
		return {
			token: "",
			korisnik:{},
			date: null,
			date2: null,
			dateSedmicni: null,
			date2Sedmicni: null,
			dateDnevni:null,
		    period: {
		      start: null,
		      end: null,
		    },
		    sedmicni: {
			      start: null,
			      end: null,
			},
			dnevni:{
				start: null,
			      end: 0,
			},
		    klinika:{},
		    lekari:[],
		    options: {
		        chart: {
		          id: 'vuechart'
		        },
		        noData: {
			    	  text: "Nema podataka",
			    	  align: 'center',
			    	  verticalAlign: 'middle',
			    	  offsetX: 0,
			    	  offsetY: 0,
			    	  style: {
			    	    color: undefined,
			    	    fontSize: '20px',
			    	    fontFamily: undefined
			    	  }
			    },
			    title: {
			        text: "Grafički prikaz pregleda",
			        align: 'center',
			        margin: 10,
			        offsetX: 0,
			        offsetY: 0,
			        floating: false,
			        style: {
			          fontSize:  '14px',
			          fontWeight:  'bold',
			          fontFamily:  undefined,
			          color:  '#263238'
			        },
			    },
			    xaxis: {
			        type: 'datetime'
			    },
			    chart: {
			        zoom: {
			            enabled: false,
			        },
			        selection: {
			            enabled: false,
			        }
			    }
		    },
		    
		    options2: {
		        chart: {
		          id: 'vuechart2'
		        },
		        noData: {
			    	  text: "Nema podataka",
			    	  align: 'center',
			    	  verticalAlign: 'middle',
			    	  offsetX: 0,
			    	  offsetY: 0,
			    	  style: {
			    	    color: undefined,
			    	    fontSize: '20px',
			    	    fontFamily: undefined
			    	  }
			    },
			    title: {
			        text: "Grafički prikaz pregleda",
			        align: 'center',
			        margin: 10,
			        offsetX: 0,
			        offsetY: 0,
			        floating: false,
			        style: {
			          fontSize:  '14px',
			          fontWeight:  'bold',
			          fontFamily:  undefined,
			          color:  '#263238'
			        },
			    },
			    xaxis: {
			        type: 'datetime'
			    },
			    chart: {
			        zoom: {
			            enabled: false,
			        },
			        selection: {
			            enabled: false,
			        }
			    }
		    },
		    
		    krofna: {
		        chart: {
		          id: 'krofna'
		        },
		        labels: [],
		        noData: {
			    	  text: "Nema podataka",
			    	  align: 'center',
			    	  verticalAlign: 'middle',
			    	  offsetX: 0,
			    	  offsetY: 0,
			    	  style: {
			    	    color: undefined,
			    	    fontSize: '20px',
			    	    fontFamily: undefined
			    	  }
			    },
			    title: {
			        text: "Grafički prikaz pregleda",
			        align: 'center',
			        margin: 10,
			        offsetX: 0,
			        offsetY: 0,
			        floating: false,
			        style: {
			          fontSize:  '14px',
			          fontWeight:  'bold',
			          fontFamily:  undefined,
			          color:  '#263238'
			        },
			    },
			    chart: {
			        zoom: {
			            enabled: false,
			        },
			        selection: {
			            enabled: false,
			        }
			    }
		    },
		    krofnaKes: {
		        chart: {
		          id: 'krofnaKes'
		        },
		        labels: [],
		        noData: {
			    	  text: "Nema podataka",
			    	  align: 'center',
			    	  verticalAlign: 'middle',
			    	  offsetX: 0,
			    	  offsetY: 0,
			    	  style: {
			    	    color: undefined,
			    	    fontSize: '20px',
			    	    fontFamily: undefined
			    	  }
			    },
			    title: {
			        text: "Grafički prikaz pregleda",
			        align: 'center',
			        margin: 10,
			        offsetX: 0,
			        offsetY: 0,
			        floating: false,
			        style: {
			          fontSize:  '14px',
			          fontWeight:  'bold',
			          fontFamily:  undefined,
			          color:  '#263238'
			        },
			    },
			    chart: {
			        zoom: {
			            enabled: false,
			        },
			        selection: {
			            enabled: false,
			        }
			    }
		    },
		    series: [{	
		    	name: "Broj pregleda",
		        data: []
		    }],
		    series2: [{	
		    	name: "Broj pregleda",
		        data: []
		    }],
		    podaciKrofne:[],
		    podaciKrofneKes:[],
		    dnevniIzvjestaj:{},
		    mjeseci:["Januar","Februar","Mart","April","Maj","Jun","Jul","Avgust","Septembar","Oktobar","Novembar","Decembar"],
		    odabraniMjesec:null,
		    izvjestaj:{}
		} 
	},
	template: 
	`
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
			<v-expansion-panels multiple>
		      <v-expansion-panel>
		        <v-expansion-panel-header>
		        	Prosečna ocena klinike
		        	<template v-slot:actions>
              			<v-icon color="primary">$expand</v-icon>
            		</template>
		        </v-expansion-panel-header>
		        <v-expansion-panel-content>
		          <table class="table table-hover table-striped">
					  	<thead class="thead-light">
					    	<tr>
						      	<th scope="col" width="10%">Naziv</th>
						      	<th scope="col" width="70%">Opis</th>
						      	<th scope="col" width="10%">Lokacija</th>
						      	<th scope="col" width="10%">Prosečna ocena</th>
					    	</tr>
					  	</thead>
					  	<tbody>
					  		<tr data-toggle="modal" data-target="#">
						      	<td width="10%">{{ klinika.naziv }}</td>
						      	<td width="70%">{{ klinika.opis }}</td>
						      	<td width="10%">{{ klinika.lokacija }}</td>
						      	<td width="10%">{{ klinika.ocena }}</td>
						  	</tr>
					  	</tbody>
					</table>
		        </v-expansion-panel-content>
		      </v-expansion-panel>
		  
		      <v-expansion-panel>
		        <v-expansion-panel-header>
		        	Prosečne ocene lekara
		        	<template v-slot:actions>
              			<v-icon color="primary">$expand</v-icon>
            		</template>
		        </v-expansion-panel-header>
		        <v-expansion-panel-content>
					<table class="table table-hover table-striped">
					  	<thead class="thead-light">
					    	<tr>
						      	<th scope="col" width="10%">Ime</th>
						      	<th scope="col" width="10%">Prezime</th>
						      	<th scope="col" width="20%">Email</th>						     
						      	<th scope="col" width="10%">Radno vreme</th>
						      	<th scope="col" width="10%">Prosečna ocena</th>
					    	</tr>
					  	</thead>
					  	<tbody>
					  		<tr v-for="lekar in lekari" data-toggle="modal" data-target="#">
						      	<td width="10%">{{ lekar.ime }}</td>
						      	<td width="10%">{{ lekar.prezime }}</td>
						      	<td width="20%">{{ lekar.email }}</td>
						      	<td width="10%">{{ radnoVreme(lekar.pocetakRadnogVremena, lekar.krajRadnogVremena) }}</td>
					    		<td width="10%">{{ lekar.prosecnaOcena }}</td>
					    	</tr>
					  	</tbody>
					</table>
		        </v-expansion-panel-content>
		      </v-expansion-panel>
		      
		      <v-expansion-panel>
		        <v-expansion-panel-header>
		        	Prikaz održanih pregleda na dnevnom nivou
		        	<template v-slot:actions>
              			<v-icon color="primary">$expand</v-icon>
            		</template>
		        </v-expansion-panel-header>
		        <v-expansion-panel-content>
		        	<div>
		        	<v-container>
		        		<v-row justify="center"
				            no-gutters
				          >
				            <v-col class="d-flex">
				              <v-menu
				                ref="startMenu2"
				                :close-on-content-click="false"
				                :return-value.sync="dnevni.start"
				                offset-y
				                min-width="290px"
				              >
				                <template v-slot:activator="{ on }">
				                  <v-text-field
				                    v-model="dnevni.start"
				                    label="Odabir datuma"
				                    prepend-icon="event"
				                    readonly
				                    v-on="on"
				                  ></v-text-field>
				                </template>
				                <v-date-picker
				                  v-model="dateDnevni"
				                  no-title
				                  scrollable
				                >
				                  <v-spacer></v-spacer>
				                  <v-btn
				                    text
				                    color="primary"
				                    @click="$refs.startMenu2.isActive = false"
				                  >Cancel</v-btn>
				                  <v-btn
				                    text
				                    color="primary"
				                    @click="$refs.startMenu2.save(dateDnevni)"
				                  >OK</v-btn>
				                </v-date-picker>
				              </v-menu>
				          	</v-col>
				          	<v-spacer></v-spacer>
				          	<v-col class="d-flex">
				          		<v-btn
								class="primary"
								v-on:click="dobaviDnevniIzvjestaj"
								>Prikaži podatke</v-btn>
				          	</v-col>
				          </v-row>	
				       </v-container>
				       <v-container>
				          	<v-row justify="space-around"
				            no-gutters>
								<v-col class="d-flex">
					         		<table class="table table-hover table-striped">
									  	<thead class="thead-light">
									    	<tr>
										      	<th scope="col" width="50%">Datum</th>
										      	<th scope="col" width="50%">Broj pregleda</th>
									    	</tr>
									  	</thead>
									  	<tbody>
									  		<tr data-toggle="modal" data-target="#">
										      	<td width="50%">{{ urediDatum(dnevniIzvjestaj.datum) }}</td>
										      	<td width="50%">{{ dnevniIzvjestaj.brojPregleda }}</td>
									    	</tr>
									  	</tbody>
									</table>	
							</v-col>  
							<v-col class="d-flex">
				         		<apexchart width="600" type="donut" :options="krofna" :series="podaciKrofne"></apexchart>  
			       			</v-col>   
			       		</v-row> 
			       	</v-container> 		
		        	</div>
		    	</v-expansion-panel-content>
		      </v-expansion-panel>
		  	  
		  	  <v-expansion-panel>
		        <v-expansion-panel-header>
		        	Prikaz održanih pregleda na nedeljnom nivou
		        	<template v-slot:actions>
              			<v-icon color="primary">$expand</v-icon>
            		</template>
		        </v-expansion-panel-header>
		        <v-expansion-panel-content> 	
		        	<div>
					     <v-row
				            justify="space-around"
				            no-gutters
				          >
				            <v-col cols="3">
				              <v-menu
				                ref="startMenu"
				                :close-on-content-click="false"
				                :return-value.sync="sedmicni.start"
				                offset-y
				                min-width="290px"
				              >
				                <template v-slot:activator="{ on }">
				                  <v-text-field
				                    v-model="sedmicni.start"
				                    label="Početni datum"
				                    prepend-icon="event"
				                    readonly
				                    v-on="on"
				                  ></v-text-field>
				                </template>
				                <v-date-picker
				                  v-model="dateSedmicni"
				                  no-title
				                  scrollable
				                >
				                  <v-spacer></v-spacer>
				                  <v-btn
				                    text
				                    color="primary"
				                    @click="$refs.startMenu.isActive = false"
				                  >Cancel</v-btn>
				                  <v-btn
				                    text
				                    color="primary"
				                    @click="$refs.startMenu.save(dateSedmicni)"				                 
				                  >OK</v-btn>
				                </v-date-picker>
				              </v-menu>
				            </v-col>
				  			<v-spacer></v-spacer>
				            <v-col cols="3">
				              <v-menu
				                ref="endMenu"
				                :close-on-content-click="false"
				                :return-value.sync="sedmicni.end"
				                offset-y
				                min-width="290px"
				              >
				                <template v-slot:activator="{ on }">
				                  <v-text-field
				                    v-model="sedmicni.end"
				                    label="Krajnji datum"
				                    prepend-icon="event"
				                    readonly
				                    v-on="on"
				                  ></v-text-field>
				                </template>
				                <v-date-picker
				                  v-model="date2Sedmicni"
				                  no-title
				                  scrollable
				                >
				                  <v-spacer></v-spacer>
				                  <v-btn
				                    text
				                    color="primary"
				                    @click="$refs.endMenu.isActive = false"
				                  >
				                    Cancel
				                  </v-btn>
				                  <v-btn
				                    text
				                    color="primary"
				                    @click="$refs.endMenu.save(date2Sedmicni)"
				                  >
				                    OK
				                  </v-btn>
				                </v-date-picker>
				              </v-menu>
				            </v-col>
				            <v-spacer></v-spacer>
				            <v-col class="d-flex">
				          		<v-btn
								class="primary"
								v-on:click="dobaviSedmicniIzvjestaj"
								>Prikaži podatke</v-btn>
				          	</v-col>
				          </v-row>
			       		
			       		<v-container>
			       			<v-row justify="center">
				         		<apexchart width="800" type="bar" :options="options" :series="series"></apexchart>  
			       			</v-row>
			       		</v-container>
		       		</div>
		        </v-expansion-panel-content>
		      </v-expansion-panel>
		      
		      
		      <v-expansion-panel>
		        <v-expansion-panel-header>
		        	Prikaz održanih pregleda na mesečnom nivou
		        	<template v-slot:actions>
              			<v-icon color="primary">$expand</v-icon>
            		</template>
		        </v-expansion-panel-header>
		        <v-expansion-panel-content> 	
		        	<div>
		        	<v-container>
							<v-row justify="space-around"
				            no-gutters>						  
							  	<v-col class="d-flex" >
								     <v-select
								     v-model="odabraniMjesec"
								     :items="mjeseci"
								     label="Odabir mjeseca"
								     ></v-select>
								</v-col>
								<v-spacer></v-spacer>
								<v-col class="d-flex">
								     <v-btn
								     class="primary"
								     v-on:click="dobaviMjesecniIzvjestaj"
								     >Prikaži podatke</v-btn>
								</v-col>
							</v-row>
					</v-container>
						<v-container>
			       			<v-row justify="center">
				         		<apexchart width="800" type="bar" :options="options2" :series="series2"></apexchart>  
			       			</v-row>
			       		</v-container>
		       		</div>
		        </v-expansion-panel-content>
		      </v-expansion-panel>
		  	
		  
		      <v-expansion-panel>
		        <v-expansion-panel-header>
		        	Prihodi klinike u određenom vremenskom periodu
		        	<template v-slot:actions>
              			<v-icon color="primary">$expand</v-icon>
            		</template>
		        </v-expansion-panel-header>
		        <v-expansion-panel-content>
		          <v-row
		            justify="space-around"
		            no-gutters
		          >
		            <v-col cols="3">
		              <v-menu
		                ref="startMenu3"
		                :close-on-content-click="false"
		                :return-value.sync="period.start"
		                offset-y
		                min-width="290px"
		              >
		                <template v-slot:activator="{ on }">
		                  <v-text-field
		                    v-model="period.start"
		                    label="Početni datum"
		                    prepend-icon="event"
		                    readonly
		                    v-on="on"
		                  ></v-text-field>
		                </template>
		                <v-date-picker
		                  v-model="date"
		                  no-title
		                  scrollable
		                >
		                  <v-spacer></v-spacer>
		                  <v-btn
		                    text
		                    color="primary"
		                    @click="$refs.startMenu3.isActive = false"
		                  >Cancel</v-btn>
		                  <v-btn
		                    text
		                    color="primary"
		                    @click="$refs.startMenu3.save(date)"
		                  >OK</v-btn>
		                </v-date-picker>
		              </v-menu>
		            </v-col>
		  			<v-spacer></v-spacer>
		            <v-col cols="3">
		              <v-menu
		                ref="endMenu3"
		                :close-on-content-click="false"
		                :return-value.sync="period.end"
		                offset-y
		                min-width="290px"
		              >
		                <template v-slot:activator="{ on }">
		                  <v-text-field
		                    v-model="period.end"
		                    label="Krajnji datum"
		                    prepend-icon="event"
		                    readonly
		                    v-on="on"
		                  ></v-text-field>
		                </template>
		                <v-date-picker
		                  v-model="date2"
		                  no-title
		                  scrollable
		                >
		                  <v-spacer></v-spacer>
		                  <v-btn
		                    text
		                    color="primary"
		                    @click="$refs.endMenu3.isActive = false"
		                  >
		                    Cancel
		                  </v-btn>
		                  <v-btn
		                    text
		                    color="primary"
		                    @click="$refs.endMenu3.save(date2)"
		                  >
		                    OK
		                  </v-btn>
		                </v-date-picker>
		              </v-menu>
		            </v-col>
		            <v-spacer></v-spacer>
				    <v-col class="d-flex">
				    	<v-btn
						class="primary"
						v-on:click="dobaviIzvjestajPoslovanja"
						>Prikaži podatke</v-btn>
				    </v-col>
		          </v-row>
		          <v-row>
		          	<v-col>
		          					<table class="table table-hover table-striped">
									  	<thead class="thead-light">
									    	<tr>
										      	<th scope="col" width="33%">Početni datum</th>
										      	<th scope="col" width="33%">Krajnji datum</th>
										      	<th scope="col" width="33%">Prihodi</th>
									    	</tr>
									  	</thead>
									  	<tbody>
									  		<tr data-toggle="modal" data-target="#">
										      	<td width="33%">{{ urediDatum(izvjestaj.start) }}</td>
										      	<td width="33%">{{ urediDatum(izvjestaj.end) }}</td>
										      	<td width="33%">{{ izvjestaj.prihod }}</td>
									    	</tr>
									  	</tbody>
									</table>
					</v-col>	
					<v-col>
				    	<apexchart width="600" type="donut" :options="krofnaKes" :series="podaciKrofneKes"></apexchart>  
			       	</v-col>   
				  </v-row>
		        </v-expansion-panel-content>
		      </v-expansion-panel>
		    </v-expansion-panels>
		</v-app>
	</div>
	`,
	created() {
		this.token = localStorage.getItem("token");
	},
	mounted: function(){
        axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data;  
        	axios
        	.get('adminiKlinike/ucitajKlinikuPoIDAdmina/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
        	.then(response=>{
        		this.klinika=response.data;
        	})
        	.catch(function (error) { console.log(error); });
        	axios
        	.get('lekari/ucitajSveLekareKlinikeByAdmin/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
        	.then(response=>{
        		this.lekari=response.data;
        	})
        	.catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	},
	methods : {
		radnoVreme : function (pocetak, kraj) {
		    minutesPoc = Math.floor((pocetak / (1000 * 60)) % 60),
		    hoursPoc = Math.floor((pocetak / (1000 * 60 * 60)) % 24);
	
			hoursPoc = (hoursPoc < 10) ? "0" + hoursPoc : hoursPoc;
			minutesPoc = (minutesPoc < 10) ? "0" + minutesPoc : minutesPoc;

			minutesKraj = Math.floor((kraj / (1000 * 60)) % 60),
		    hoursKraj = Math.floor((kraj / (1000 * 60 * 60)) % 24);
	
			hoursKraj = (hoursKraj < 10) ? "0" + hoursKraj : hoursKraj;
			minutesKraj = (minutesKraj < 10) ? "0" + minutesKraj : minutesKraj;

			return hoursPoc + ":" + minutesPoc + " - " + hoursKraj + ":" + minutesKraj;
		},
		dobaviSedmicniIzvjestaj: function(){
			if(this.sedmicni.start==null || this.sedmicni.end==null){
				toast("Morate izabrati oba datuma.");
				return;
			}
			else{
				let a=Date.parse(this.sedmicni.start);
				let b=Date.parse(this.sedmicni.end);
				if(a>b){
					toast("Krajnji datum mora biti veći od početnog datuma.");
					return;
				}
				if(a==b){
					toast("Interval mora biti veći od jednog dana.");
					return;
				}
				if((b-a)/86400000>10){
					toast("Nije moguće izabrati interval veći od 10 dana.");
					return;
				}
				else{
					let izvjestaj={id:this.klinika.id,start:a,end:b};
					axios
					.post('klinike/sedmicniIzvjestaj/',izvjestaj,{ headers: { Authorization: 'Bearer ' + this.token }})
					.then(response=>{
						let odgovor=response.data;
						for(let a of odgovor){
							a[0]=new Date(a[0]);
							a[1]=Math.trunc(a[1]);
						}
						this.series = [{
						    data: odgovor
						   }]
					})
					.catch(function (error) { console.log(error); })
				}
				
			}
		},
		dobaviDnevniIzvjestaj: function(){
			if(this.dnevni.start==null){
				toast("Morate izabrati datum.");
				return;
			}
			var a=Date.parse(this.dnevni.start);
			var izvjestaj={id:this.klinika.id,start:a,end:0};
			axios
			.post('klinike/dnevniIzvjestaj/',izvjestaj,{ headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				let dnevni={datum:response.data.ukupno[0],brojPregleda:response.data.ukupno[1]}
				this.dnevniIzvjestaj=dnevni;
				let labele=[];
				let podaci=[];
				for (let [key, value] of Object.entries(response.data.krofna)) {
					 labele.push(key);
					 podaci.push(value);
				}
				this.krofna={labels:labele};
				this.podaciKrofne=podaci;
			})
			.catch(function (error) { console.log(error); })
		},
		urediDatum:function(datum){
			if(datum==null){
				return "";
			}
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            return datum
        },
        dobaviMjesecniIzvjestaj:function(){
        	if(this.odabraniMjesec==null){
        		toast("Morate izabrati mjesec.")
        	}
        	var izvjestaj={id:this.klinika.id,mjesec:this.odabraniMjesec};
        	axios
        	.post('klinike/mjesecniIzvjestaj/',izvjestaj,{ headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				let odgovor=response.data;
				for(let a of odgovor){
					a[0]=new Date(a[0]);
					a[1]=Math.trunc(a[1]);
				}
				this.series2 = [{
				    data: odgovor
				   }]
			})
			.catch(function (error) { console.log(error); })
        },
        dobaviIzvjestajPoslovanja: function(){
        	if(this.period.start==null || this.period.end==null){
        		toast("Morate izabrati oba datuma.");
				return;
			}
			else{
				let a=Date.parse(this.period.start);
				let b=Date.parse(this.period.end);
				if(a>b){
					toast("Krajnji datum mora biti veći od početnog datuma.");
					return;
				}
				if(a==b){
					toast("Interval mora biti veći od jednog dana.");
					return;
				}
				else{
					let izvjestaj={id:this.klinika.id,start:a,end:b};
					axios
					.post('klinike/izvjestajPoslovanja/',izvjestaj,{ headers: { Authorization: 'Bearer ' + this.token }})
					.then(response=>{
						this.izvjestaj=response.data;
						let labele=[];
						let podaci=[];
						for (let [key, value] of Object.entries(response.data.krofna)) {
							 labele.push(key);
							 podaci.push(value);
						}
						this.krofnaKes={labels:labele};
						this.podaciKrofneKes=podaci;
					})
					.catch(function (error) { console.log(error); })
				}
				
			}
        }
	}
});