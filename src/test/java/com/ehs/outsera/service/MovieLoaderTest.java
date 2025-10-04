package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieLoaderTest {

    @InjectMocks
    MovieLoader movieLoader;
    @Mock
    MovieService movieService;
    @Mock
    ProducerService producerService;

    @Captor
    ArgumentCaptor<List<Movie>> captor;

    @Test
    void shouldParseAndSaveCsv() {
        String csv = """
                year;title;studios;producers;winner
                2000;Movie1;Studio1;Producer A;yes
                2001;Movie2;Studio2;Producer B;
                """;
        ByteArrayInputStream in = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));

        movieLoader.parseAndSave(in);

        verify(movieService).saveAllMovies(captor.capture());

        List<Movie> values = captor.getValue();
        assertThat(values).hasSize(2);
        assertThat(values.get(0).getTitle()).isEqualTo("Movie1");
        assertThat(values.get(0).getWinner()).isTrue();
        assertThat(values.get(1).getTitle()).isEqualTo("Movie2");
        assertThat(values.get(1).getWinner()).isFalse();
    }

}
