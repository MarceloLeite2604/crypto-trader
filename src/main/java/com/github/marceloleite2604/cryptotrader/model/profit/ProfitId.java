package com.github.marceloleite2604.cryptotrader.model.profit;

import com.github.marceloleite2604.cryptotrader.model.Active;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
@Embeddable
@EqualsAndHashCode
public class ProfitId implements Serializable {

  private final String accountId;

  private final Active active;
}
