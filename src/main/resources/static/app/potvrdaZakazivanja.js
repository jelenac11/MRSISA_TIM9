Vue.component('potvrda-zakazivanja', {
	data : function() {
		return {
			token: "",
			pregled : {},
			zaposleni: false,
			dijalogGreska: false,
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
			<div class="naviga container d-flex justify-content-center">
				<div class="card mt-5" style="width: 50rem;">
					<h4 class="card-header">Detalji o pregledu</h4>
					<div class="card-body">
						<ul class="list-group">
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Ime:</h6>
							  		<p class="mb-0">{{ this.pregled.pacijent.ime }}</p>
							  	</div>
							  	<div class="d-flex w-20 justify-content-between">
							  		<h6>Prezime:</h6>
							  		<p class="mb-0">{{ this.pregled.pacijent.prezime }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Klinika:</h6>
							  		<p class="mb-0">{{ this.pregled.lokacija.naziv }}</p>
							  	</div>
							  	<div class="d-flex w-20 justify-content-between">
							  		<h6>Lokacija:</h6>
							  		<p class="mb-0">{{ this.pregled.lokacija.lokacija }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Datum:</h6>
						  			<p class="mb-0">{{ urediDatum(this.pregled.datum) }}</p>
						  		</div>
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Tip pregleda:</h6>
						  			<p class="mb-0">{{ this.pregled.tip.naziv }}</p>
						  		</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Lekar:</h6>
						  			<p class="mb-0">{{ this.pregled.lekar.ime }} {{this.pregled.lekar.prezime}}</p>
						  		</div>
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Cena:</h6>
						  			<p class="mb-0">{{ this.pregled.cena }}</p>
						  		</div>
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Popust:</h6>
						  			<p class="mb-0">{{ this.pregled.popust }}%</p>
						  		</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
						  			<button type="button" style="color:white" class="btn btn-secondary" v-on:click="nazad()">Nazad</button>
						  			<button type="button" style="color:white" class="btn btn-success" v-on:click="potvrdi()">Potvrdi</button>
						  		</div>
						  	</li>
						</ul>
					</div>
				</div>
			</div>
			<v-dialog v-model="dijalogGreska" max-width="300">
		      <v-card>
		        <v-card-title class="headline">Greška</v-card-title>
		        <v-card-text>Neko drugi je upravo zakazao ovaj termin pre vas.</v-card-text>
		        <v-card-actions>
		          <v-spacer></v-spacer>
		          <v-btn color="green darken-1" text @click="dijalogGreska = false; $router.push({ name: 'zdravstveniKarton'});">u redu</v-btn>
		        </v-card-actions>
		      </v-card>
		    </v-dialog>
		</v-app>
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
		potvrdi: function() {
			dani = this.pregled.datum % 86400000;
			datum = this.pregled.datum - dani - 7200000;
			axios
			.put("/pregledi/potvrdiZakazivanje", this.pregled, { headers: { Authorization: 'Bearer ' + this.token }} )
			.then(response => {
				if (!response.data) {
					this.dijalogGreska = true;
				} else {
					toast("Uspešno potvrđeno zakazivanje!"); 
					if (this.zaposleni){
						this.$router.push({ name: 'zaposleni', params: {id: this.pregled.lokacija.id}});
					} else {
						this.$router.push({ name: 'pretragaLekara', params: {id: this.pregled.lokacija.id, vreme : datum, tip : this.pregled.tip.naziv}});
				}
			}})
			.catch(function (error) { console.log(error); });
		},
		nazad: function() {
			dani = this.pregled.datum % 86400000;
			datum = this.pregled.datum - dani - 7200000;
			if (this.zaposleni){
				this.$router.push({ name: 'zaposleni', params: {id: this.pregled.lokacija.id}});
			} else {
				this.$router.push({ name: 'pretragaLekara', params: {id: this.pregled.lokacija.id, vreme : datum, tip : this.pregled.tip.naziv}});
			}
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		this.pregled = this.$route.params.pregled;
		this.zaposleni = this.$route.params.zaposleni;
	}
})