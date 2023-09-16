package com.hoshogi.onlyonepick.domain.mail.repository;

import com.hoshogi.onlyonepick.domain.mail.entity.Mail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRedisRepository extends CrudRepository<Mail, String> {
}
