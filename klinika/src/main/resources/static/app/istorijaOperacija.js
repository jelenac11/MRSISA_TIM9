Vue.component("istorija-operacija", {
	data : function() {
		return {
			ulogovan: {},
			operacije: [],
			activeTab: '2',
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
			<div data-app>
				<div class="naviga">			    
					<v-tabs v-model="activeTab" centered>
				      	<v-tab href="#1" v-on:click="promijeniTab(1)">
							<router-link :to="{ name: 'istorijaPregleda' }">Pregledi</router-link>
				      	</v-tab>
				      	<v-tab href="#2" v-on:click="promijeniTab(2)">
							<router-link :to="{ name: 'istorijaOperacija' }">Operacije</router-link>
				      	</v-tab>
				    </v-tabs>
				</div>
				<div class="naviga tab-content">
					<table class="table table-hover table-striped sortable" id="tabela">
					  	<thead class="thead-light">
					    	<tr>
						      	<th scope="col" width="20%"><button class="btn">Klinika</button></th>
						      	<th data-dateformat="DD/MM/YYYY h:mm:ss a" scope="col" width="25%"><button class="btn">Vreme</button></th>
						      	<th scope="col" width="40%"><button class="btn">Lekari</button></th>
						      	<th scope="col" width="15%"><button class="btn">Broj sale</button></th>
					    	</tr>
					  	</thead>
					  	<tbody>
					  		<tr v-for="operacija in operacije">
						      	<td width="20%">{{ operacija.klinika.naziv }}</td>
						      	<td width="25%">{{ urediDatum(operacija.vreme2) }}</td>
						      	<td width="40%">{{ ispisiLekare(operacija.lekari) }}</td>
						      	<td width="15%">{{ operacija.sala.broj }}</td>
					    	</tr>
					  	</tbody>
					</table>
				</div>
			</div>
		</v-app>
	</div>
	`
	,
	methods : {
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'numeric', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		promijeniTab : function (a) {
			if (a == 1) {
				this.$router.replace({ name: 'istorijaPregleda' });
			} else {
				this.$router.replace({ name: 'istorijaOperacija' });
			}
		},
		ispisiLekare: function (lekari) {
			var lekString = "";
			for (let l of lekari) {
				lekString += l.ime + " " + l.prezime + ", ";
			}
			return lekString.substring(0, lekString.length - 2);
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
            .get('/operacije/ucitajSveOperacijePacijenta/' + this.ulogovan.id,{ headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.operacije = response.data))
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	},
	mounted() {
		$.bootstrapSortable({ applyLast: true });
		$("#tabela").on('sorted', function () { 
			if (!this.sortirano) {
				this.sortirano = true;
				$.bootstrapSortable({ applyLast: true });
			} 
		});
	}
});