Vue.component("lekari", {
	data : function() {
		return {
			lekari: [],
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="12%">Ime</th>
				      	<th scope="col" width="12%">Prezime</th>
				      	<th scope="col" width="20%">Email</th>
				      	<th scope="col" width="12%">Adresa</th>
				      	<th scope="col" width="10%">Grad</th>
				      	<th scope="col" width="10%">Država</th>
				      	<th scope="col" width="12%">Broj telefona</th>
				      	<th scope="col" width="12%">Radno vreme</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="lekar in lekari" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="12%">{{ lekar.ime }}</td>
				      	<td width="12%">{{ lekar.prezime }}</td>
				      	<td width="20%">{{ lekar.email }}</td>
				      	<td width="12%">{{ lekar.adresa }}</td>
				      	<td width="10%">{{ lekar.grad }}</td>
				      	<td width="10%">{{ lekar.drzava }}</td>
				      	<td width="12%">{{ lekar.brojTelefona }}</td>
				      	<td width="12%">{{ radnoVreme(lekar.pocetakRadnogVremena, lekar.krajRadnogVremena) }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeLekara' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeLekara">Dodaj novog Lekara</router-link>
		</div>
	</div>
	`
	,
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
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('lekari/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.lekari = response.data))
        .catch(function (error) { console.log(error); });
	}
});