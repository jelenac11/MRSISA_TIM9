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
				      	<th scope="col" width="14%">Ime</th>
				      	<th scope="col" width="18%">Prezime</th>
				      	<th scope="col" width="22%">Email</th>
				      	<th scope="col" width="22%">Adresa</th>
				      	<th scope="col" width="12%">Grad</th>
				      	<th scope="col" width="12%">Država</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="lekar in lekari" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="14%">{{ lekar.ime }}</td>
				      	<td width="18%">{{ lekar.prezime }}</td>
				      	<td width="22%">{{ lekar.email }}</td>
				      	<td width="22%">{{ lekar.adresa }}</td>
				      	<td width="12%">{{ lekar.grad }}</td>
				      	<td width="12%">{{ lekar.drzava }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeLekara', params: { korisnikToken: this.token } }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeLekara">Dodaj novog Lekara</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = this.$route.params.korisnikToken;
		axios
        .get('lekari/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.lekari = response.data))
        .catch(function (error) { console.log(error); });
	}
});