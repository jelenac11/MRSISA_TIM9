Vue.component("klinike-admin", {
	data : function() {
		return {
			klinike: [],
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
				      	<th scope="col" width="40%">Naziv</th>
				      	<th scope="col" width="40%">Lokacija</th>
				      	<th scope="col" width="20%">Ocena</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="klinika in klinike" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="40%">{{ klinika.naziv }}</td>
				      	<td width="40%">{{ klinika.lokacija }}</td>
				      	<td width="20%">{{ klinika.ocena }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeKlinike', params: { korisnikToken: this.token } }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeKlinike">Dodaj novu kliniku</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
        .get('klinike/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.klinike = response.data))
        .catch(function (error) { console.log(error); });
	}
});