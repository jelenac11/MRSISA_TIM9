Vue.component("pacijenti", {
	data : function() {
		return {
			pacijenti: [],
			token: "",
			sortirano: false,
			korisnik: {},
			imePrezime: "",
			prezime: "",
			jbo: "",
		} 
	},
	template: `
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga tab-content">
			<div class="naviga tab-pane fade show active" id="pills-pk" role="tabpanel">
				<div class="input-group">
					<input type="search" class="form-control col-3 ml-auto m-2" style="height:40px"  v-model="imePrezime"  placeholder="Ime i prezime..."/>
					<input type="search" class="form-control col-3 my-2 mr-2" style="height:40px"  v-model="jbo"  placeholder="JBO..."/>
				</div>
			</div>
			<table id="tabela" class="table table-hover table-striped sortable">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="30%"><button class="btn">Ime</button></th>
				      	<th scope="col" width="30%"><button class="btn">Prezime</button></th>
				      	<th scope="col" width="30%"><button class="btn">JBO</button></th>
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
			.then(response=> {
				this.pacijenti = response.data;
			})
			.catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
		$.bootstrapSortable({ applyLast: true });
		$("#tabela").on('sorted', function () { 
			if (!this.sortirano) {
				this.sortirano = true;
				$.bootstrapSortable({ applyLast: true });
			} 
		});
	},
	methods:{
		pristupiPacijentu : function (p) {
			localStorage.setItem("pacijent", JSON.stringify(p));
			this.$root.$router.push({ name: 'profilPacijenta'})
		},
	},
	created() {
		this.token = localStorage.getItem("token");
	},
	computed:{
		filtriraniPacijenti: function(){
			return this.pacijenti.filter ( pacijent => {
				return (pacijent.ime.toLowerCase().includes(this.imePrezime.toLowerCase().trim()) || pacijent.prezime.toLowerCase().includes(this.imePrezime.toLowerCase().trim())) && pacijent.jbo.toString().toLowerCase().includes(this.jbo.toLowerCase().trim());
			})
		}
	}
});