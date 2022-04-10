package org.programmer.kdtspringboot.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class BlackListUserRepository implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(BlackListUserRepository.class);
    private final String fileName = "customer_blacklist.csv";
    private final FileWriter fileWriter = new FileWriter(fileName, true);
    private final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    private final FileReader fileReader = new FileReader(fileName);
    private final BufferedReader bufferedReader = new BufferedReader(fileReader);

    public BlackListUserRepository() throws IOException {
    }

    @Override
    public void saveUser(User user) {
        String content = user.getUserId() + "," + user.getUserName();
        try {
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("블랙리스트유저 추가(" + user.getUserId() + "," + user.getUserName() + ")");
    }

    @Override
    public List<User> findAll() throws IOException {
        List<User> list = new ArrayList<>();
        String content = null;
        while ((content = bufferedReader.readLine()) != null) {
            String[] contents = content.split(",");
            list.add(new BlackListUser(UUID.fromString(contents[0]), contents[1]));
        }
        logger.info("CSV File list 반환 성공");
        return list;
    }
}
