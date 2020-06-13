Vue.component("radni-kalendar", {
	data : function() {
		return {
			token : "",
			ulogovan : {},
			type: 'month',
		    types: ['month', 'week', '4day', 'day'],
		    weekday: [0, 1, 2, 3, 4, 5, 6],
		    dialog: false,
		    mozeZapoceti: false,
		    mozeOtkazati: false,
		    dogadjaj: {},
		    value: '',
		    events: [],
		} 
	},
	template: `
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
			<div data-app>
				<div class="naviga tab-content">
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
		  			  		@click:event="showEvent"
		  			  		@click:date="viewDay"
			        	></v-calendar>
		      		</v-sheet>
		      	</div>
		      	
		      	<v-dialog width="500" v-model="dialog">
					<v-card>
						<v-card-title>
		        			{{ dogadjaj.name }}
		        		</v-card-title>
		        		<v-spacer></v-spacer>
		        		<v-list-item-subtitle>
		        			<p class="m-3 h6" v-if="dogadjaj.name == 'Pregled'">Tip pregleda: {{ dogadjaj.tipPregleda }}</br></p>
		        			<p class="m-3 h6">Pacijent: {{ dogadjaj.pacijent }}</br></p>
		        			<p class="m-3 h6">Sala: {{ dogadjaj.sala }}</br></p>
		        		</v-list-item-subtitle>
		        		<v-btn class="m-3" v-if="dogadjaj.name == 'Pregled'" v-on:click="zapocniPregled" :disabled="!mozeZapoceti">
							Započni pregled
						</v-btn>
						<v-btn class="m-3" v-if="dogadjaj.name == 'Pregled'" v-on:click="otkazi" :disabled="!mozeOtkazati">
							Otkaži pregled
						</v-btn>
						<v-btn class="m-3" v-if="dogadjaj.name == 'Operacija'" v-on:click="otkazi" :disabled="!mozeOtkazati">
							Otkaži operaciju
						</v-btn>
					</v-card>
				</v-dialog>
				
			</div>
		</v-app>
	</div>
	`
	,
	methods : {
		formatVreme : function(datum) {
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', { day: 'numeric', month: 'numeric', year: 'numeric' }).replace(/ /g, '-');
            datum = datum + " " + date.toLocaleTimeString();
            return datum;
        },
        zapocniPregled : function () {
        	axios
    		.get('/pacijenti/' + this.dogadjaj.pacijentId, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	localStorage.setItem("pacijent", JSON.stringify(response.data));
            	this.$router.replace({ name: 'zapocniPregled' });
            })
            .catch(function (error) { console.log(error); });
		},
		getEventColor : function (event) {
			return event.color;
		},
		formatDatumKalendar : function (a) {
			let datum = `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
			return datum;
		},
		viewDay : function ({ date }) {
	        this.focus = date
	        this.type = 'day'
		},
		showEvent : function ({ nativeEvent, event }) {
			if (event.name == "Pregled" || event.name == "Operacija") {
				this.dogadjaj = event;
				this.daLiMozeZapoceti();
				this.daLiMozeOtkazati();
				this.dialog = true;
			}
		},
		daLiMozeZapoceti : function () {
			if (this.dogadjaj.pacijentId == 0) {
				this.mozeZapoceti = false;
			} else {
				let sada = new Date().getTime();
				if (this.dogadjaj.pocetak <= sada && this.dogadjaj.kraj >= sada) {
					this.mozeZapoceti = true;
				}
			}
		},
		daLiMozeOtkazati: function() {
			let sada = new Date().getTime();
			if (this.dogadjaj.pocetak - 86400000 > sada) {
				this.mozeOtkazati = true;
			} else {
				this.mozeOtkazati = false;
			}
		},
		otkazi : function() {
			if (this.dogadjaj.name == 'Pregled') {
				axios
				.put("/pregledi/otkaziPregledLekara", { id: this.ulogovan.id, datum: this.dogadjaj.pocetak }, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => { 
					this.ukloniDogadjaj();
					this.dialog = false;
				})
		        .catch(function (error) { console.log(error); });
			} else if (this.dogadjaj.name == 'Operacija') {
				axios
				.put("/operacije/otkaziOperacijuLekara", { id: this.ulogovan.id, datum: this.dogadjaj.pocetak }, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => { 
					this.ukloniDogadjaj();
					this.dialog = false;
				})
		        .catch(function (error) { console.log(error); });
			}
		},
		ukloniDogadjaj : function() {
			const indeks = this.events.indexOf(this.dogadjaj);
			if (indeks > -1) {
				this.events.splice(indeks, 1);
			}
		}
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data; })
        .catch(function (error) { console.log(error); });

		axios.
		get('zaposleni/dobaviRadniKalendar/', { headers: { Authorization: 'Bearer ' + this.token }})
		.then(response => {
			let events = response.data;
			const kalendar = [];
			for (let e of events) {
				let kolor = "";
				if (e.naziv == "Godisnji odmor") {
					kolor = "yellow";
				} else if (e.naziv == "Operacija") {
					kolor = "green";
				} else if (e.naziv == "Pregled") {
					kolor = "blue";
				} 
				kalendar.push({
		            name: e.naziv,
		            start: this.formatDatumKalendar(new Date(e.start)),
	            	end: this.formatDatumKalendar(new Date(e.end)),
	            	pocetak: e.start,
	            	kraj: e.end,
	            	color: kolor,
	            	pacijentId: e.pacijentId,
	            	pacijent: e.pacijent,
	            	tipPregleda: e.tipPregleda,
	            	lekari: e.lekari,
	            	sala: e.sala
		        });
			}
			this.events = kalendar;
		})
		.catch(function (error) { console.log(error); });
	}
});