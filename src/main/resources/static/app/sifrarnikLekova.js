Vue.component("sifrarnik-lekova", {
	data : function() {
		return {
			sifrarnik: {},
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
				      	<th scope="col" width="30%">Šifra</th>
				      	<th scope="col" width="70%">Naziv leka</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="stavka in sifrarnik.stavke" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="30%">{{ stavka.sifra }}</td>
				      	<td width="70%">{{ stavka.naziv }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeSifreLeka' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeStavke">Dodaj novu stavku</router-link>
		</div>
	</div>
	`
	,
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('sifrarnici/ucitajSifrarnikLekova', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.sifrarnik = response.data))
        .catch(function (error) { console.log(error); });
	}
});