Vue.component("zakazani-pregledi", {
	data : function() {
		return {
			zakazaniPregledi: [],
			token: "",
			activeTab:"1",
		} 
	},
	template: `
	<v-app>
	<div data-app> 
	<navig-bar v-bind:token="this.token"></navig-bar>
		<div>			    
			<v-tabs v-model="activeTab" centered>
		      <v-tab href="#1" v-on:click="promijeniTab(1)">
				<router-link :to="{ name: 'zakazaniPregledi' }">Zakazani pregledi</router-link>
		      </v-tab>
		      <v-tab href="#2" v-on:click="promijeniTab(2)">
				<router-link :to="{ name: 'definisanjeSlobodnogTermina' }">Slobodni termini</router-link>
		      </v-tab>
		      <v-tab href="#3" v-on:click="promijeniTab(3)">
				<router-link :to="{ name: 'naCekanjuTermini' }">Pregledi bez sale</router-link>
		      </v-tab>
		    </v-tabs>
		</div>
		<div class="tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="25%">Pacijent</th>
				      	<th scope="col" width="25%">Lekar</th>
				      	<th scope="col" width="10%">Vreme</th>
				      	<th scope="col" width="15%">Sala</th>
				      	<th scope="col" width="15%">Tip pregleda</th>
				      	<th scope="col" width="10%">Trajanje</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="pregled in zakazaniPregledi">
				      	<td width="25%">{{ pregled.pacijent.ime }} {{ pregled.pacijent.prezime }}</td>
				      	<td width="25%">{{ pregled.lekar.ime }} {{ pregled.lekar.prezime }}</td>
				      	<td width="10%">{{formatVreme(pregled.vreme)}}</td>
				      	<td width="15%">{{ pregled.sala.naziv }}</td>
				      	<td width="15%">{{ pregled.tipPregleda.naziv }}</td>
				      	<td width="10%">{{ formatTrajanje(pregled.trajanje) }}</td>
			    	</tr>
			  	</tbody>
			</table>
			</div>
	</div>
	</v-app>
	`
	,
	methods : {
		childMethod:function(){
			axios
            .get('/pregledi/ucitajSveZakazanePreglede/'+this.ulogovan.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.zakazaniPregledi = response.data))
            .catch(function (error) { console.log(error); });
		},
		formatVreme:function(datum){
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            datum=datum+" "+date.toLocaleTimeString()
            return datum
        },
        formatTrajanje: function(trajanje){
			return parseInt(trajanje)/60000+ " minuta"
		},
		promijeniTab: function(a){
			if(a==1){
				this.$router.replace({ name: 'zakazaniPregledi' });
			}
			else if(a==2){
				this.$router.replace({ name: 'definisanjeSlobodnogTermina' });
			}
			else{
				this.$router.replace({ name: 'naCekanjuTermini' });
			}
		}
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
            .get('/pregledi/ucitajSveZakazanePreglede/'+this.ulogovan.id ,{ headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.zakazaniPregledi = response.data))
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	}
});