Vue.component("izmena-profilaKlinike", {
	data : function() {
		 return {
			klinika : {lat:0,lng:0} ,
	    	korisnik : {},
	    	izmenjenaKlinika : {lat:0,lng:0},
	    	submitovano : false,
	    	uspesnaIzmena : true,
	    	token : "",
	    	uspesno: true,
	    	markers: [],
	        place: null,
			center: {
                lat: 0,
                lng: 0,
            },
            zoom:15,
            tacnaLokacija:false,
            trenutnaLokacija:'',
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 70rem;">
			<h4 class="card-header">Izmena podataka klinike</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmenaPod" id="forma-izmena">
					<div class="form-row">
						<div class="col">
				    	 	<label for="naziv">Naziv</label>
							<input type="text" v-model="izmenjenaKlinika.naziv" class="form-control" id="naziv" v-bind:class="{ 'is-invalid': !uspesno}" placeholder="Naziv" required>
							<div class="invalid-feedback" id="izmenaInvalid">Uneti naziv je neispravan ili zauzet.</div>
						</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="lokacija" class="mt-1">Lokacija</label>
							<GmapAutocomplete id="lok" required placeholder="Unesite lokaciju" @place_changed="setPlace" class="form-control">
						    </GmapAutocomplete>
							<div class="invalid-feedback" id="izmenaInvalid">Niste uneli lokaciju.</div>
						</div>
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
										lat: izmenjenaKlinika.lat,
										lng: izmenjenaKlinika.lng,
									}"
							        />
							    </GmapMap>				    					    	
				    	</div>
				  	</div>
				  	<div v-if=tacnaLokacija class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Uneta lokacija je nevalidna. Pokušajte ponovo.</p>
					</div>
				    <div class="form-row">
				    	<div class="col">
				    		<label for="opis" class="mt-1">Opis</label>
							<input type="text" v-model="izmenjenaKlinika.opis" class="form-control" id="opis" placeholder="Opis">
				    	</div>
					  	<button style="color:white;" class="btn btn-lg btn-primary btn-block mt-4" type="submit" v-bind:disabled="izmenjenaKlinika.naziv == klinika.naziv && izmenjenaKlinika.lokacija == klinika.lokacija && izmenjenaKlinika.opis == klinika.opis">
					  		Sačuvaj izmene
					  	</button>
				  	</div>
				</form>
				<button class="btn btn-lg btn-secondary btn-block mt-4" v-on:click="nazad">Nazad</button>
			</div>
		</div>
	</div>	
	`
	,
	methods : {
		izmenaPod : function () {
			this.submitovano = true;
			if (document.getElementById('forma-izmena').checkValidity() === true) {
				if(this.markers.length==0 || this.izmenjenaKlinika.lokacija!=$("#lok").val()){
					this.tacnaLokacija=true;
					return;
				}
				else{
					this.tacnaLokacija=false;
				}
				axios
				.post('/klinike/izmenaProfilaKlinike', this.izmenjenaKlinika, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					
					this.uspesno = response.data;
					if (this.uspesno) {
						this.uspesnaIzmena = true;
						toast("Uspešno izmenjeni podaci klinike.");
					}
					else{
						this.uspesnaIzmena = false;
						toast("Neuspešno izmenjeni podaci klinike.");
					}
				})
				.catch(function (error) { console.log(error);this.uspesnaIzmena = false; });
			} else {
				this.uspesnaIzmena = true;
				if(this.markers.length==0 || this.izmenjenaKlinika.lokacija!=$("#lok").val()){
					this.tacnaLokacija=true;
					return;
				}
				else{
					this.tacnaLokacija=false;
				}
			}
		},
		nazad : function () {
            this.$router.replace({ name: 'lekari', params: { korisnikToken: this.token } });
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
		    this.izmenjenaKlinika.lat=this.place.geometry.location.lat();
		    this.izmenjenaKlinika.lng=this.place.geometry.location.lng();
		    this.markers.push(position);
		    this.center = position;
		    this.zoom=15;
		    this.izmenjenaKlinika.lokacija=$("#lok").val()
		    this.tacnaLokacija=false;

		},
	},
	mounted () {
		this.token = localStorage.getItem("token");
		this.klinika=this.$route.params.klinikaParam;
		this.izmenjenaKlinika=JSON.parse(JSON.stringify(this.klinika));
		this.center.lat=this.izmenjenaKlinika.lat;
		this.center.lng=this.izmenjenaKlinika.lng;
		$("#lok").val(this.izmenjenaKlinika.lokacija);
	}
});