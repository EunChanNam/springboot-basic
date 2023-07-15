package org.programmers.VoucherManagement.member.infrastructure;


import org.programmers.VoucherManagement.global.file.FileInfo;
import org.programmers.VoucherManagement.global.util.MemberConverter;
import org.programmers.VoucherManagement.member.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileMemberRepository {
    private final File file;
    private final Logger logger = LoggerFactory.getLogger(Console.class);

    public FileMemberRepository(FileInfo fileInfo) {
        this.file = new File(fileInfo.getFilePath());
    }

    public List<Member> findAllByMemberStatus() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> lines = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            return MemberConverter.toMembers(lines);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("해당하는 파일을 찾을 수 없습니다.", e);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }
    }
}
