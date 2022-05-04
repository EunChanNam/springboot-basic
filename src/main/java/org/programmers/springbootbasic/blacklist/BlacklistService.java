package org.programmers.springbootbasic.blacklist;

import org.programmers.springbootbasic.blacklist.model.Blacklist;
import org.programmers.springbootbasic.blacklist.repository.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<Blacklist> findAll() {
        return blacklistRepository.findAll();
    }
}