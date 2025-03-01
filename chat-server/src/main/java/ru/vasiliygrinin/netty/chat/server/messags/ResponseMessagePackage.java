package ru.vasiliygrinin.netty.chat.server.messags;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ResponseMessagePackage {

    private int idHandlers;

    private String message;

}
