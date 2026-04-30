package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.repository.LeituraTarotRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeituraTarotService implements ILeituraTarotService {

    @Autowired
    private LeituraTarotRepository repository;

    @Override
    public LeituraTarot salvarLeitura(User user, TarotCard carta, String leituraTexto) {
        LeituraTarot novaLeitura = new LeituraTarot();
        novaLeitura.setUser(user);
        novaLeitura.setCartaTarot(carta);
        novaLeitura.setLeitura(leituraTexto);
        novaLeitura.setDataNascimentoUser(user.getDataNascimento());
        
        return repository.save(novaLeitura);
    }

    @Override
    public Optional<LeituraTarot> buscarLeituraDeHoje(User user) {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime fimDia = LocalDate.now().atTime(LocalTime.MAX);

        return repository.findByUserAndDataLeituraBetween(user, inicioDia, fimDia);
    }

    @Override
    public boolean verificarLeituraDoDia(User user) {
        return buscarLeituraDeHoje(user).isPresent();
    }
}
