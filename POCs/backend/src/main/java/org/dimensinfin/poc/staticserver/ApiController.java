package org.dimensinfin.poc.staticserver;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

    @Autowired
    private Message message;

    @GetMapping(path = "/api/message")
    @ResponseBody
    public String message() {

        return message.get("Titulo de prueba").toString();
    }

    @Component
    private static class Message {
        private String title;

        public Message get(final String title) {
            this.title = title;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("title", title)
                    .toString();
        }
    }
}