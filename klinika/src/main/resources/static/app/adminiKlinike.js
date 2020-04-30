Vue.component("admini-klinike", {
	data : function() {
		return {
			admini: [],
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
				      	<th scope="col" width="14%">Ime</th>
				      	<th scope="col" width="14%">Prezime</th>
				      	<th scope="col" width="20%">Email</th>
				      	<th scope="col" width="20%">Adresa</th>
				      	<th scope="col" width="12%">Grad</th>
				      	<th scope="col" width="10%">Država</th>
				      	<th scope="col" width="10%">Klinika</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="adm in admini" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="14%">{{ adm.ime }}</td>
				      	<td width="14%">{{ adm.prezime }}</td>
				      	<td width="20%">{{ adm.email }}</td>
				      	<td width="20%">{{ adm.adresa }}</td>
				      	<td width="12%">{{ adm.grad }}</td>
				      	<td width="10%">{{ adm.drzava }}</td>
				      	<td width="10%">{{ adm.klinika }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeAdminaKlinike', params: { korisnikToken: this.token } }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeAdminaKlinike">Dodaj novog admina klinike</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
        .get('adminiKlinike/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.admini = response.data))
        .catch(function (error) { console.log(error); });
	}
});