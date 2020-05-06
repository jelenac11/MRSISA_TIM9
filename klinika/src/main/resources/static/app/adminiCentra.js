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
		
		<div class="tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="14%">Ime</th>
				      	<th scope="col" width="18%">Prezime</th>
				      	<th scope="col" width="22%">Email</th>
				      	<th scope="col" width="22%">Adresa</th>
				      	<th scope="col" width="12%">Grad</th>
				      	<th scope="col" width="12%">Država</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="adm in admini" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="14%">{{ adm.ime }}</td>
				      	<td width="18%">{{ adm.prezime }}</td>
				      	<td width="22%">{{ adm.email }}</td>
				      	<td width="22%">{{ adm.adresa }}</td>
				      	<td width="12%">{{ adm.grad }}</td>
				      	<td width="12%">{{ adm.drzava }}</td>
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