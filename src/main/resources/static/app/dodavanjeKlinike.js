Vue.component("dodavanje-klinike", {
	data : function() {
		return {
			novaKlinika : {
				naziv : "",
				lokacija : "",
				opis : "",
				ocena : 0,
				lat:0,
				lng:0,
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	zauzetoIme : false,
	    	token : "",
	    	markers: [],
	        place: null,
			center: {
                lat: 0,
                lng: 0,
            },
            zoom:3,
            tacnaLokacija:false,
            trenutnaLokacija:'',
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova klinika</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajKliniku" id="forma-dodaj-kliniku">
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="naz" class="mt-1">Naziv</label>
							<input type="text" v-model="novaKlinika.naziv" class="form-control" id="naz" placeholder="Naziv" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli naziv.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="op" class="mt-1">Opis</label>
							<textarea v-model="novaKlinika.opis" class="form-control" id="op" placeholder="Opis"></textarea>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="lok" class="mt-1">Lokacija</label>
				    		<GmapAutocomplete id="lok" required placeholder="Unesite lokaciju" @place_changed="setPlace" class="form-control">
						    </GmapAutocomplete>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli lokaciju.</div>
				    	</div>
				  	</div>
				  	<div v-if=zauzetoIme class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Već postoji klinika sa unetim imenom. Pokušajte ponovo.</p>
					</div>
					<div v-if=tacnaLokacija class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Uneta lokacija je nevalidna. Pokušajte ponovo.</p>
					</div>
					
					<div class="form-row">
				    	<div class="col">
							    <GmapMap style="width: 600px; height: 300px;" :zoom="zoom" :center="center">
							      <GmapMarker v-for="(marker, index) in markers"
							        :key="index"
							        :position="marker.position"
							        />
							      <GmapMarker
							        v-if="this.place"
							        label="★"
							        :position="{
							          lat: this.place.geometry.location.lat(),
							          lng: this.place.geometry.location.lng(),
							        }"
							        />
							    </GmapMap>
				    	
				    	
				    	</div>
				  	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'klinikeAdmin' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajKliniku : function () {
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-kliniku').checkValidity() === true) {
				if(this.markers.length==0 || this.novaKlinika.lokacija!=$("#lok").val()){
					this.tacnaLokacija=true;
					return;
				}
				else{
					this.tacnaLokacija=false;
				}
				axios
				.get('/klinike/proveriIme/' + this.novaKlinika.naziv, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					axios
					.post('klinike', this.novaKlinika, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then(response => {
						this.uspesnoDodavanje = response.data;
						
						if (this.uspesnoDodavanje) {
							this.$router.replace({ name: 'klinikeAdmin' });
						}
					})
					.catch(error => {
						console.log(error);
						this.uspesnoDodavanje = false;
					});
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
					this.zauzetoIme = true;
				});
			} else {
				if(this.markers.length==0 || this.novaKlinika.lokacija!=$("#lok").val()){
					this.tacnaLokacija=true;
					return;
				}
				else{
					this.tacnaLokacija=false;
				}
				this.uspesnoDodavanje = true;
				this.zauzetoIme = false;
			}
		},
		setDescription(description) {
		      this.description = description;
		    },
		setPlace(place) {
		    this.place = place
		    this.markers=[]
		    let position= {
		            lat: this.place.geometry.location.lat(),
		            lng: this.place.geometry.location.lng(),
		          }
		    this.novaKlinika.lat=this.place.geometry.location.lat();
		    this.novaKlinika.lng=this.place.geometry.location.lng();
		    this.markers.push(position);
		    this.center = position;
		    this.zoom=12;
		    this.novaKlinika.lokacija=$("#lok").val()
		    this.tacnaLokacija=false;

		},
	},
	mounted() {
		this.token = localStorage.getItem("token");
	},
});