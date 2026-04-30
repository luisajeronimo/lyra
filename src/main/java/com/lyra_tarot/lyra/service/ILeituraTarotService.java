package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import java.util.Optional;

public interface ILeituraTarotService {
    LeituraTarot salvarLeitura(User user, TarotCard carta, String leitura);
    Optional<LeituraTarot> buscarLeituraDeHoje(User user);
    boolean verificarLeituraDoDia(User user);
}
