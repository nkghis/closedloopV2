package ci.nkagou.closedloop.service.impl;

import ci.nkagou.closedloop.service.PinService;
import ci.nkagou.closedloop.utils.BCrypt;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PinServiceImpl implements PinService {


    @Override
    public String hashPinCode(String originalPinCode) {
        String generatedSecuredPinCodeHash = BCrypt.hashpw(originalPinCode, BCrypt.gensalt(12));
        return generatedSecuredPinCodeHash;
    }

    @Override
    public Boolean checkPinCode(String originalPinCode, String hashPinCode) {
        boolean matched = BCrypt.checkpw(originalPinCode, originalPinCode);
        return matched;
    }
}
