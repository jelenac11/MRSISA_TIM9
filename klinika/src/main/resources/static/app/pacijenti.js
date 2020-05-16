Vue.component("pacijenti", {
	data : function() {
		return {
			pacijenti: [],
			token: "",
			korisnik: {},
			ime: "",
			prezime: "",
			jbo: "",
		} 
	},
	template: `
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga tab-content">
			<div class="naviga tab-pane fade show active" id="pills-pk" role="tabpanel" >
				<div class="input-group">
					<input type="search" class="form-control col-4 ml-auto m-2" v-model="ime"  placeholder="Ime..."/>
					<input type="search" class="form-control col-4 ml-auto m-2" v-model="prezime"  placeholder="Prezime..."/>
					<input type="search" class="form-control col-4 ml-auto m-2" v-model="jbo"  placeholder="JBO..."/>
				</div>
			</div>
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="30%">Ime</th>
				      	<th scope="col" width="30%">Prezime</th>
				      	<th scope="col" width="30%">JBO</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="pacijent in filtriraniPacijenti" data-toggle="modal" data-target="#" v-on:click="pristupiPacijentu(pacijent)">
				      	<td width="10%">{{ pacijent.ime }}</td>
				      	<td width="10%">{{ pacijent.prezime }}</td>
				      	<td width="20%">{{ pacijent.jbo }}</td>
				 	</tr>
			  	</tbody>
			</table>
		</div>
	</div>
	`
	,
	mounted: function(){
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data;
			axios
			.get('pacijenti/dobaviSvePoIdKlinike/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }})
			.then(response=>{
				this.pacijenti=response.data;
			})
			.catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	},
	methods:{
		pristupiPacijentu: function(p){
			localStorage.setItem("pacijent", JSON.stringify(p));
			this.$root.$router.push({ name: 'profilPacijenta'})
		}
	},
	created() {
		this.token = localStorage.getItem("token");
	},
	computed:{
		filtriraniPacijenti: function(){
			return this.pacijenti.filter ( pacijent => {
				return pacijent.ime.toLowerCase().includes(this.ime.toLowerCase().trim()) && pacijent.prezime.toLowerCase().includes(this.prezime.toLowerCase().trim()) && pacijent.jbo.toString().toLowerCase().includes(this.jbo.toLowerCase().trim());
			})
		}
	}
});