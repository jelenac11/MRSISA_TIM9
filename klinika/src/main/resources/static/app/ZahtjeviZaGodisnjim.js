Vue.component("zahtjevGodisnji",{
	data : function(){
		return {
            zahtjevi : null,
            indeks:null
		}
	},
	
    template: `
<div>
        <h1>
            Zahtevi za odsustvom
        </h1>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Ime</th>
                    <th scope="col">Prezime</th>
                    <th scope="col">Početak odsustva</th>
                    <th scope="col">Kraj odsustva</th>
                    <th scope="col" colspan="2">Akcija</th>
                </tr>

            </thead>
            <tbody>
                <tr v-for="(zahtjev,indeks) in zahtjevi">
                    <th scope="row">{{indeks+1}}</th>
                    <td>{{zahtjev.osoba.ime}}</td>
                    <td>{{zahtjev.osoba.prezime}}</td>
                    <td>{{urediDatum(zahtjev.pocetak_odsustva)}}</td>
                    <td>{{urediDatum(zahtjev.kraj_odsustva)}}</td>
                    <td><button type="button" class="btn btn-success" v-on:click="prihvatanjeZahtjeva(indeks)">Prihvati zahtev</button></td>
                    <td><button type="button" class="btn btn-danger" data-target="#obrazlozenjeModal" data-toggle="modal" v-on:click="zabiljeziIndeks(indeks)">Odbij zahtev</button></td>
                </tr>
            </tbody>
        </table>
        <div class="modal fade" id="obrazlozenjeModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
	    	<div class="modal-content">
				<div class="modal-header">
	        		<h5 class="modal-title" id="exampleModalLabel">Obrazlozenje odbijanja zahteva</h5>
	        		<button type="button" class="close" data-dismiss="modal">
	          			<span>&times;</span>
	        		</button>
	      		</div>
	      		<div class="modal-body">
                  <input id="obrazlozenje" type="text" class="form-control" placeholder="Unesite obrazlozenje odbijanja zahteva">
	      		</div>
	      		<div class="modal-footer">
	        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
	        		<button class="btn btn-primary" data-dismiss="modal" v-on:click="odbijanjeZahtjeva">Odbij zahtev</button>
	      		</div>
	    	</div>
		</div>
		</div>
    </div>
	`
		,
	mounted (){
        this.dobaviZahtjeve()
	},
	methods:
    {
		prihvatanjeZahtjeva: function(indeks){
			this.zahtjevi[indeks].status=true
            this.zahtjevi[indeks].stanje=true
            this.updateZahtjev(indeks)
        },
        zabiljeziIndeks: function(indeks){
            this.indeks=indeks;
        },
		odbijanjeZahtjeva: function(){
			this.zahtjevi[this.indeks].status=true
            this.zahtjevi[this.indeks].stanje=false
            this.zahtjevi[this.indeks].obrazlozenje=$("#obrazlozenje").val()
            this.updateZahtjev(this.indeks)
        },
        updateZahtjev: function(indeks){
            axios.post('/api/updateZahtjev',this.zahtjevi[indeks]).then(response=>{this.dobaviZahtjeve()});
        },
        dobaviZahtjeve: function(){
            axios.get('/api/getZahteviNaCekanju').then(response => 
                {
                    this.zahtjevi = response.data
                })
                .catch(function (error) { console.log(error); });
        },
        urediDatum:function(datum){
            var date = new Date(datum);
            datum=date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            return datum
        },
	}
});