Vue.component("zahtjevGodisnji",{
	data : function(){
		return {
            zahtjevi: null,
            indeks: null,
            ulogovan: {},
            submitovano: false,
            token: "",
            korisnik:"",
            dijalog:false,
		}
	},
    template: `
	<div>
    	<navig-bar v-bind:token="this.token"></navig-bar>
    	<v-app>
    	<div class="naviga">
		    <table class="table table-hover table-striped">
		        <thead class="thead-light">
		            <tr>
		                <th scope="col">Ime</th>
		                <th scope="col">Prezime</th>
		                <th scope="col">Početak odsustva</th>
		                <th scope="col">Kraj odsustva</th>
		                <th scope="col" colspan="2">Akcija</th>
		            </tr>
		
		        </thead>
		        <tbody>
		            <tr v-for="(zahtjev,indeks) in zahtjevi">
		                <td>{{zahtjev.podnosilac.ime}}</td>
		                <td>{{zahtjev.podnosilac.prezime}}</td>
		                <td>{{urediDatum(zahtjev.pocetak)}}</td>
		                <td>{{urediDatum(zahtjev.kraj)}}</td>
		                <td><button type="button" class="btn btn-success" v-on:click="prihvatanjeZahtjeva(indeks)">Prihvati zahtev</button></td>
		                <td><button type="button" class="btn btn-danger" @click="dijalog = true" v-on:click="zabiljeziIndeks(indeks)">Odbij zahtev</button></td>
		            </tr>
		        </tbody>
		    </table>
			<v-dialog v-model="dijalog">
				<v-card>
					<div class="naviga">
						<form class="needs-validation mb-4"  id="forma-obrazlozenje-odbijanja">
				    	<div class="modal-content">
							<div class="modal-header">
				        		<h5 class="modal-title" id="exampleModalLabel">Obrazlozenje odbijanja zahteva</h5>
				      		</div>
				      		<div class="modal-body">
				              	<input id="obrazlozenje" type="text" class="form-control" placeholder="Unesite obrazlozenje odbijanja zahteva" required>
				      			<div class="invalid-feedback" id="dodavanjeInvalid">Unesite razlog odbijanja zahteva za odsustvom.</div>
				      		</div>
				      		<div class="modal-footer">
				        		<button type="button" class="btn btn-secondary mr-auto" @click="dijalog = false">Nazad</button>
				        		<button class="btn btn-primary" v-on:click="odbijanjeZahtjeva">Odbij zahtev</button>
				      		</div>
				    	</div>
				    </form>
					</div>	
				</v-card>		
			</v-dialog>
		</div>
		</v-app>
    </div>
	`
	,
	created(){
    	this.token = localStorage.getItem("token");
    	axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data
        	this.dobaviZahtjeve();
        })
        .catch(function (error) { console.log(error); });
	},
	methods:
    {
		prihvatanjeZahtjeva: function(indeks){
			this.zahtjevi[indeks].odgovoreno=true
            this.zahtjevi[indeks].odobreno=true
            this.zahtjevi[indeks].obrazlozenje=""
            this.updateZahtjev(indeks)
        },
        zabiljeziIndeks: function(indeks){
            this.indeks=indeks;
            $("#obrazlozenje").val("");
            this.submitovano=false;
        },
		odbijanjeZahtjeva: function(){
			if (document.getElementById('forma-obrazlozenje-odbijanja').checkValidity() === true){
				this.zahtjevi[this.indeks].odgovoreno=true
	            this.zahtjevi[this.indeks].odobreno=false
	            this.zahtjevi[this.indeks].obrazlozenje=$("#obrazlozenje").val()
	            this.updateZahtjev(this.indeks)
			}
        },
        updateZahtjev: function(indeks){
            axios.put('/odsustva/updateOdsustvo', this.zahtjevi[indeks], { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response=>{ this.dijalog=false;this.dobaviZahtjeve()})
            .catch(function (error) { console.log(error); });
        },
        dobaviZahtjeve: function() {
            axios.get('/odsustva/ucitajSvaNeodgovorenaOdsustva/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { this.zahtjevi =response.data; })
            .catch(function (error) { console.log(error); });
        },
        urediDatum:function(datum){
            var date = new Date(datum);
            datum = date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            return datum
        },
	}
});