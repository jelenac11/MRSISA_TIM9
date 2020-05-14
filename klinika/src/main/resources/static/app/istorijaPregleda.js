Vue.component("istorija-pregleda", {
	data : function() {
		return {
			ulogovan: {},
			pregledi: [],
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
				      	<th scope="col" width="20%">Klinika</th>
				      	<th scope="col" width="20%">Tip pregleda</th>
				      	<th scope="col" width="20%">Vreme</th>
				      	<th scope="col" width="20%">Lekar</th>
				      	<th scope="col" width="10%">Broj sale</th>
				      	<th scope="col" width="10%"></th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="(pregled, indeks) in pregledi">
				      	<td width="20%">{{ pregled.klinika.naziv }}</td>
				      	<td width="20%">{{ pregled.tipPregleda.naziv }}</td>
				      	<td width="20%">{{ urediDatum(pregled.vreme2) }}</td>
				      	<td width="20%">{{ pregled.lekar.ime }} {{pregled.lekar.prezime}}</td>
				      	<td width="10%">{{ pregled.sala.broj }}</td>
			    		<td width="10%"><button class="btn btn-danger btn-sm" v-on:click="otkaziPregled(indeks)" id="brisanjeTipa" v-show="proveriOtkazivanje(indeks)">Otkaži</button></td>
			    	</tr>
			  	</tbody>
			</table>
		</div>
	</div>
	`
	,
	methods : {
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
		ucitajPreglede : function() {
			axios
            .get('/pregledi/ucitajSvePregledePacijenta/'+this.ulogovan.id,{ headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.pregledi = response.data))
            .catch(function (error) { console.log(error); });
		},
		proveriOtkazivanje : function(indeks) {
			datum = new Date();
			timestamp = datum.getTime();
			if (this.pregledi[indeks].vreme2 - 86400000 > timestamp) {
				return true;
			} else {
				return false;
			}
		},
		otkaziPregled : function(indeks) {
			var hoceDaOtkaze = confirm("Da li ste sigurni da želite otkazati pregled? ");
			if (hoceDaOtkaze) {
				axios
				.put("/pregledi/otkaziPregled", this.pregledi[indeks], { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					if (response.data) {
						toast("Uspešno otkazan pregled.");
						this.ucitajPreglede();
					}
				})
	            .catch(function (error) { console.log(error); });
			}
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	this.ucitajPreglede();
        })
        .catch(function (error) { console.log(error); });
	}
});