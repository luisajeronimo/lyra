package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.repository.LeituraTarotRepository;
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
}
