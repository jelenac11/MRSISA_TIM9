package tim09.klinika.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;

public interface LekarRepository extends JpaRepository<Lekar, Long> {

	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'LE' and k.klinika_id = ?1 and (select count(l) from specijalizovan l where l.lekar_id=k.korisnik_id and l.tip_pregleda_id=?2)=1 and "
			+ "(select count(o) from odsustvo o where o.podnosilac_id=k.korisnik_id and (o.pocetak <= ?3 and  ?3 <= o.kraj))=0", nativeQuery = true)
	List<Lekar> findBySearchParams(Long id, Long pregledId, Long datum);
	
	@Query(value = "SELECT * FROM korisnici k WHERE k.tip = 'LE' and k.klinika_id = ?1", nativeQuery = true)
	List<Lekar> findByKlinikaId(Long id);
	
	List<Lekar> findAllByKlinika(Klinika k);
	
	@Query(value="select * from korisnici l where l.tip='LE' and l.klinika_id=?1 and ?3 between l.pocetak_radnog_vremena and l.kraj_radnog_vremena and "
			+ "(select count(svi) from specijalizovan svi where svi.lekar_id=l.korisnik_id and svi.tip_pregleda_id=?4)=1 and "
			+ "(select count(pr) from pregled pr where pr.lekar_id=l.korisnik_id and (pr.vreme between ?2 and (?2+?5) or (pr.vreme+pr.trajanje) between ?2 and (?2+?5)))=0 and "
			+ "(select count(god) from odsustvo god where god.podnosilac_id=l.korisnik_id and (?2 between god.pocetak and god.kraj or (?2+?5) between god.pocetak and god.kraj))=0 and "
			+ "(select count(ope) from operacija ope where (select op.lekar_id from operisali op where op.lekar_id=l.korisnik_id and op.operacija_id=ope.operacija_id)=l.korisnik_id and ope.vreme between ?2 and (?2+?5))=0", nativeQuery = true)
		List<Lekar> findByIdKlinikaAndVremeAndTipPregleda(long klinikaId, long datumiVreme,long vreme, long tipPregledaId,long trajanje);

}
