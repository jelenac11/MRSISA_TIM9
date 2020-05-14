Vue.component("sale", {
	data : function() {
		return {
			ulogovan: {},
			sale: [],
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
				      	<th scope="col" width="20%">Broj</th>
				      	<th scope="col" width="80%">Naziv</th>
				      	<th></th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="sala in sale" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="33%">{{ sala.broj }}</td>
				      	<td width="33%">{{ sala.naziv }}</td>
				      	<td width="33%"><button class="btn btn-danger btn-sm" v-on:click="obrisiSalu(sala)" id="brisanjeSale">Ukloni</button></td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeSale' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeSale">Dodaj novu salu</router-link>
		</div>
	</div>
	`
	,
	methods:{
		obrisiSalu:function(sala){
			axios
			.delete('sale/'+sala.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response => {
				if(response.data==true){
					toast("Uspesno izbrisana sala!");
					axios
			        .get('/sale/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
			        .then(response => (this.sale = response.data))
			        .catch(function (error) { console.log(error); });
				}
				else{
					toast("Nije moguce izbrisati salu!")
				}
			})
	        .catch(function (error) { console.log(error); });
		}
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('/sale/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.sale = response.data))
        .catch(function (error) { console.log(error); });
	}
});