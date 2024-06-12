package com.example.board.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long boardid;
    private String username;
    private String comment;
    private Timestamp commentdate;
    private Long commentsort;
    private Long margin;
}
