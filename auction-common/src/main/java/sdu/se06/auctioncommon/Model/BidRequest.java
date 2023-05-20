package sdu.se06.auctioncommon.Model;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BidRequest {

    private int id;
    private int listingID;
    private int userID;
    private double amount;
    private String source;
    public Date time;

    public BidRequestState accountbidRequestState = BidRequestState.CREATED;
    public BidRequestState catalogBidRequestState = BidRequestState.CREATED;
    public BidRequestState sagaStatus = BidRequestState.CREATED;


}
