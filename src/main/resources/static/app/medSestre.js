Vue.component("medSestre", {
	data : function() {
		return {
			medSestre: [],
			search: "",
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="naviga tab-content">
			<div class="input-group">
				<input type="search" class="form-control col-4 ml-auto m-2" v-model="search"  placeholder="Ime i prezime..."/>
			</div>
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
			  		<tr v-for="sestra in filtriraneMedSestre" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="12%">{{ sestra.ime }}</td>
				      	<td width="12%">{{ sestra.prezime }}</td>
				      	<td width="20%">{{ sestra.email }}</td>
				      	<td width="12%">{{ sestra.adresa }}</td>
				      	<td width="10%">{{ sestra.grad }}</td>
				      	<td width="10%">{{ sestra.drzava }}</td>
				      	<td width="12%">{{ sestra.brojTelefona}}</td>
				      	<td width="12%">{{ radnoVreme(sestra.pocetakRadnogVremena, sestra.krajRadnogVremena) }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeMedSestara' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeMedSestara">Dodaj novu medicinsku sestru</router-link>
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
        .get('sestre/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.medSestre = response.data))
        .catch(function (error) { console.log(error); });
	},
	computed : {
		filtriraneMedSestre : function() {
			return this.medSestre.filter(seka => {
				return seka.ime.toLowerCase().includes(this.search.toLowerCase().trim()) || seka.prezime.toLowerCase().includes(this.search.toLowerCase().trim());
			})
		}
	}
});