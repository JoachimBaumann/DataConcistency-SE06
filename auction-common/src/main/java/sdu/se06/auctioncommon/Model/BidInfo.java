package sdu.se06.auctioncommon.Model;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter @Getter
public class BidInfo {
    private int listingID;
    private double balance;
    private List<BidRequest> bids;


}
