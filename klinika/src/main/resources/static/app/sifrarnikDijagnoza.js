Vue.component("sifrarnik-dijagnoza", {
	data : function() {
		return {
			sifrarnik: {},
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
				      	<th scope="col" width="30%">Šifra</th>
				      	<th scope="col" width="70%">Naziv dijagnoze</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="stavka in sifrarnik.stavke" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="30%">{{ stavka.sifra }}</td>
				      	<td width="70%">{{ stavka.naziv }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeSifreDijagnoze' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeStavke">Dodaj novu stavku</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('sifrarnici/ucitajSifrarnikDijagnoza', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.sifrarnik = response.data))
        .catch(function (error) { console.log(error); });
	}
});