Vue.component("zakazane-operacije", {
	data : function() {
		return {
			zakazaneOperacije: [],
			token: "",
			activeTab:"2",
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
						<router-link :to="{ name: 'zakazaniPregledi' }">Zakazani pregledi</router-link>
				      </v-tab>
				      <v-tab href="#2" v-on:click="promijeniTab(2)">
						<router-link :to="{ name: 'zakazaneOperacije' }">Zakazane operacije</router-link>
				      </v-tab>
				      <v-tab href="#3" v-on:click="promijeniTab(3)">
						<router-link :to="{ name: 'definisanjeSlobodnogTermina' }">Predefinisani termini</router-link>
				      </v-tab>
				      <v-tab href="#4" v-on:click="promijeniTab(4)">
						<router-link :to="{ name: 'naCekanjuTermini' }">Pregledi bez sale</router-link>
				      </v-tab>
				      <v-tab href="#5" v-on:click="promijeniTab(5)">
						<router-link :to="{ name: 'naCekanjuOperacije' }">Operacije bez sale</router-link>
				      </v-tab>
				    </v-tabs>
				</div>
				<div class="naviga tab-content">
					<table class="table table-hover table-striped">
					  	<thead class="thead-light">
					    	<tr>
						      	<th scope="col" width="20%">Pacijent</th>
						      	<th scope="col" width="25%">Lekari</th>
						      	<th scope="col" width="25%">Vreme</th>
						      	<th scope="col" width="15%">Sala</th>
						      	<th scope="col" width="15%">Trajanje</th>
					    	</tr>
					  	</thead>
					  	<tbody>
					  		<tr v-for="operacija in zakazaneOperacije">
						      	<td width="20%">{{ operacija.pacijent.ime }} {{ operacija.pacijent.prezime }}</td>
						      	<td width="25%">{{ ispisiLekare(operacija.lekari) }}</td>
						      	<td width="25%">{{formatVreme(operacija.vreme)}}</td>
						      	<td width="15%">{{ operacija.sala.naziv }}</td>
						      	<td width="15%">60 minuta</td>
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
		childMethod : function () {
			axios
            .get('/operacije/ucitajSveZakazaneOperacije/' + this.ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.zakazaneOperacije = response.data))
            .catch(function (error) { console.log(error); });
		},
		formatVreme : function (datum) {
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            	day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            datum = datum + " " + date.toLocaleTimeString();
            return datum;
        },
        formatTrajanje : function (trajanje) {
			return parseInt(trajanje) / 60000 + " minuta";
		},
		ispisiLekare: function (lekri) {
			var lekString = "";
			for (let l of lekri) {
				lekString += l.ime + " " + l.prezime + ", ";
			}
			return lekString.substring(0, lekString.length - 2);
		},
		promijeniTab : function (a) {
			if (a == 1) {
				this.$router.replace({ name: 'zakazaniPregledi' });
			}
			else if(a == 2) {
				this.$router.replace({ name: 'zakazaneOperacije' });
			}
			else if(a == 3) {
				this.$router.replace({ name: 'definisanjeSlobodnogTermina' });
			}
			else if (a == 4) {
				this.$router.replace({ name: 'naCekanjuTermini' });
			} 
			else {
				this.$router.replace({ name: 'naCekanjuOperacije' });
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
            .get('/operacije/ucitajSveZakazaneOperacije/' + this.ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.zakazaneOperacije = response.data))
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	}
});