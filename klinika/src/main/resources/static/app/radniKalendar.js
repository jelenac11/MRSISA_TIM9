Vue.component("radni-kalendar", {
	data : function() {
		return {
			token : "",
			ulogovan : {},
			type: 'month',
		    types: ['month', 'week', '4day', 'day'],
		    weekday: [0, 1, 2, 3, 4, 5, 6],
		    value: '',
		    events: [],
		} 
	},
	template: `
	<v-app>
		<div data-app>
			<navig-bar v-bind:token="this.token"></navig-bar>
			
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
		        	></v-calendar>
	      		</v-sheet>
	      	</div>
		</div>
	</v-app>	
	`
	,
	methods : {
		getEventColor : function (event) {
			return event.color;
		},
		formatDatumKalendar : function (a) {
			let datum = `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
			return datum;
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data; })
        .catch(function (error) { console.log(error); });

		axios.
		get('zaposleni/dobaviRadniKalendar/', { headers: { Authorization: 'Bearer ' + this.token }})
		.then(response=>{
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
	            	color: kolor,
		        });
			}
			this.events = kalendar;
		})
		.catch(function (error) { console.log(error); });
	}
});