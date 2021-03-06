Vue.component("admini-centra", {
	data : function() {
		return {
			admini: [],
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="naviga tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="15%">Ime</th>
				      	<th scope="col" width="15%">Prezime</th>
				      	<th scope="col" width="20%">Email</th>
				      	<th scope="col" width="20%">Adresa</th>
				      	<th scope="col" width="15%">Grad</th>
				      	<th scope="col" width="15%">Država</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="adm in admini" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="15%">{{ adm.ime }}</td>
				      	<td width="15%">{{ adm.prezime }}</td>
				      	<td width="20%">{{ adm.email }}</td>
				      	<td width="20%">{{ adm.adresa }}</td>
				      	<td width="15%">{{ adm.grad }}</td>
				      	<td width="15%">{{ adm.drzava }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeAdminaCentra' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeAdminaCentra">Dodaj novog admina centra</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('adminiCentra/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.admini = response.data))
        .catch(function (error) { console.log(error); });
	}
});