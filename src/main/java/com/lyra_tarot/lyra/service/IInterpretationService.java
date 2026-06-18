package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.dto.TarotCardDTO;
import com.lyra_tarot.lyra.model.User;

public interface IInterpretationService {
    public String interpretarCartaDoDia(User user, TarotCardDTO cartas);

}
