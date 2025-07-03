/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils.email;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
public class Email {
    private String from = "";
    private String to = "";
    private String cc = "";
    private String bcc = "";
    private String subject = "";

    @ToString.Exclude
    private String content = "";
    private List<Attachment> attachments = new ArrayList<>();

    public static EmailBuilder builder() {
        return new EmailBuilder();
    }

    public static class EmailBuilder {
        private final List<Attachment> attachments = new ArrayList<>();
        private String from = "";
        private String to = "";
        private String cc = "";
        private String bcc = "";
        private String subject = "";
        private String content = "";

        public static EmailBuilder builder() {
            return new EmailBuilder();
        }

        public EmailBuilder to(String... emails) {
            this.to = "";
            return addTo(emails);
        }

        public EmailBuilder from(String from) {
            this.from = from;
            return this;
        }

        public EmailBuilder addTo(String... emails) {
            this.to = joinEmailsInString(this.to, emails);
            return this;
        }

        public EmailBuilder cleanTo() {
            this.to = "";
            return this;
        }


        public EmailBuilder cc(String... emails) {
            this.cc = "";
            return addCc(emails);
        }

        public EmailBuilder addCc(String... emails) {
            this.cc = joinEmailsInString(this.cc, emails);
            return this;
        }

        public EmailBuilder cleanCc() {
            this.cc = "";
            return this;
        }

        public EmailBuilder bcc(String... emails) {
            this.bcc = "";
            return addBcc(emails);
        }

        public EmailBuilder addBcc(String... emails) {
            this.bcc = joinEmailsInString(this.bcc, emails);
            return this;
        }

        private String joinEmailsInString(String currentEmails, String[] emails) {
            List<String> currentEmailsList = new ArrayList<>();
            if (StringUtils.isNotBlank(currentEmails)) {
                String[] ccEmailsSplit = StringUtils.split(currentEmails, ",");
                String[] checkIfArrayIsNullAndConvertToEmptyArray = ArrayUtils.nullToEmpty(ccEmailsSplit);
                List<String> listOfSplitAndFilteredCcEmails = Stream.of(checkIfArrayIsNullAndConvertToEmptyArray)
                        .map(StringUtils::trimToEmpty)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toList());
                currentEmailsList.addAll(listOfSplitAndFilteredCcEmails);
            }
            currentEmailsList.addAll(Stream.of(ArrayUtils.nullToEmpty(emails)).map(StringUtils::trimToEmpty).filter(StringUtils::isNotBlank).collect(Collectors.toList()));

            currentEmails = currentEmailsList.stream().reduce("", (accumulatorStringBuilder, nextString) -> accumulatorStringBuilder.concat(nextString).concat(","));
            currentEmails = StringUtils.removeEndIgnoreCase(currentEmails, ",");
            currentEmails = StringUtils.removeStartIgnoreCase(currentEmails, ",");
            return currentEmails;
        }

        public EmailBuilder cleanBcc() {
            this.bcc = "";
            return this;
        }

        public EmailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder content(String content) {
            this.content = content;
            return this;
        }

        public EmailBuilder attachments(Attachment... attachments) {
            this.attachments.clear();
            return addAttachments(attachments);
        }

        public EmailBuilder addAttachments(Attachment... attachments) {
            if (isNull(attachments)) {
                attachments = new Attachment[]{};
            }

            List<Attachment> attachmentList = Arrays.asList(attachments);

            this.attachments.addAll(attachmentList);

            return this;
        }

        public EmailBuilder cleanAttachments() {
            this.attachments.clear();
            return this;
        }


        public Email build() {
            Email email = new Email();

            email.setFrom(this.from);
            email.setTo(this.to);
            email.setCc(this.cc);
            email.setBcc(this.bcc);
            email.setSubject(this.subject);
            email.setContent(this.content);
            email.setAttachments(this.attachments);

            return email;
        }
    }
}
