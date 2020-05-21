Vue.component('zahtev-odsustvo', {
	data : function() {
		return {
			token:"",
			ulogovan:{},
			odsustvo:{pocetak:'',kraj:'',podnosilac:null},
			date:null,
			date2:null,
		}
	},
	template:
	`
	<div>
		<navig-bar v-bind:token="this.token"></navig-bar>
		<v-app>
			<div class="naviga">
				<div class="container d-flex justify-content-center">
					<div class="card mt-5" style="width: 70rem;">
						<h4 class="card-header">Zahtev za odsustvom</h4>
						<div class="card-body">
							<ul class="list-group">
							  	<li class="list-group-item">
							  		<v-row
						            justify="space-around"
						            no-gutters
						          >
						            <v-col cols="3">
						              <v-menu
						                ref="startMenu3"
						                :close-on-content-click="false"
						                :return-value.sync="odsustvo.pocetak"
						                offset-y
						                min-width="290px"
						              >
						                <template v-slot:activator="{ on }">
						                  <v-text-field
						                    v-model="odsustvo.pocetak"
						                    label="Početni datum"
						                    prepend-icon="event"
						                    readonly
						                    v-on="on"
						                  ></v-text-field>
						                </template>
						                <v-date-picker
						                  v-model="date"
						                  no-title
						                  scrollable
						                >
						                  <v-spacer></v-spacer>
						                  <v-btn
						                    text
						                    color="primary"
						                    @click="$refs.startMenu3.isActive = false"
						                  >Cancel</v-btn>
						                  <v-btn
						                    text
						                    color="primary"
						                    @click="$refs.startMenu3.save(date)"
						                  >OK</v-btn>
						                </v-date-picker>
						              </v-menu>
						            </v-col>
						  
						            <v-col cols="3">
						              <v-menu
						                ref="endMenu3"
						                :close-on-content-click="false"
						                :return-value.sync="odsustvo.kraj"
						                offset-y
						                min-width="290px"
						              >
						                <template v-slot:activator="{ on }">
						                  <v-text-field
						                    v-model="odsustvo.kraj"
						                    label="Krajnji datum"
						                    prepend-icon="event"
						                    readonly
						                    v-on="on"
						                  ></v-text-field>
						                </template>
						                <v-date-picker
						                  v-model="date2"
						                  no-title
						                  scrollable
						                >
						                  <v-spacer></v-spacer>
						                  <v-btn
						                    text
						                    color="primary"
						                    @click="$refs.endMenu3.isActive = false"
						                  >
						                    Cancel
						                  </v-btn>
						                  <v-btn
						                    text
						                    color="primary"
						                    @click="$refs.endMenu3.save(date2)"
						                  >
						                    OK
						                  </v-btn>
						                </v-date-picker>
						              </v-menu>
						            </v-col>
						          </v-row>
							  	</li>
							</ul>
						</div>
						<div>
							<button class="btn btn-lg btn-primary" v-on:click="posaljiZahtev">Pošalji zahtev</button>
							<button class="btn btn-lg btn-secondary" style="float: right;" v-on:click="nazad">Nazad</button>
						</div>
					</div>
				</div>
			</div>
		</v-app>
	</div>
	`,
	created() {
		this.token = localStorage.getItem("token");
	},
	mounted() {
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data;this.odsustvo.podnosilac=this.ulogovan})
        .catch(function (error) { console.log(error); });
	},
	methods:{
		posaljiZahtev: function(){
			if(this.odsustvo.pocetak=='' || this.odsustvo.kraj==''){
				toast("Morate izabrati oba datuma.");
				return;
			}
			let a=Date.parse(this.odsustvo.pocetak)
			let b=Date.parse(this.odsustvo.kraj)
			if(a<(new Date()) || b< (new Date())){
				toast("Datum mora biti posle današnjeg datuma.");
				return;
			}
			if(a>=b){
				toast("Datum početka ne može biti nakon krajnjeg datuma.");
				return;
			}
			let zahtev=JSON.parse(JSON.stringify(this.odsustvo));
			zahtev.pocetak=Date.parse(zahtev.pocetak)
			zahtev.kraj=Date.parse(zahtev.kraj)
			axios
			.post('zaposleni/zahtevOdsustvo',zahtev, { headers: { Authorization: 'Bearer ' + this.token }} )
			.then(response=>{
				if(response.data==true){
					toast("Zahtev za odsustvom je poslat.");
				}
				else{
					toast("Nije moguće dobiti odsustvo u željenom periodu zbog već zakazanih obaveza.");
				}
			})
			.catch(function (error) { console.log(error); });
		},
		nazad: function(){
			this.$router.replace({ name: 'pacijenti' });
		}
	}
});