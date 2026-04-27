package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;

public interface ILeituraTarotService {
    LeituraTarot salvarLeitura(User user, TarotCard carta, String leitura);
}
