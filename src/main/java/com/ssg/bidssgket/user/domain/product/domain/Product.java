package com.ssg.bidssgket.user.domain.product.domain;

import com.ssg.bidssgket.common.domain.BaseTimeEntity;
import com.ssg.bidssgket.user.domain.auction.domain.Auction;
import com.ssg.bidssgket.user.domain.member.domain.Review;
import com.ssg.bidssgket.user.domain.member.domain.Wishlist;
import com.ssg.bidssgket.user.domain.order.domain.PurchaseOrder;
import com.ssg.bidssgket.user.domain.order.domain.SaleOrder;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import jakarta.persistence.*;
import lombok.*;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNo;
    private String productName;
    private String productDesc;
    private Boolean imd_purchase;
    private Boolean auction_selected;
    private Integer buynowPrice;
    private Integer auctionStartPrice;
    private Integer bidSuccessPrice;
    private LocalDateTime auctionStartTime;
    private LocalDateTime auctionEndTime;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Sales_status salesStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<ProductImage>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductReport> productReports = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Auction> aucitons = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "reviewNo")
    private Review review;

    @OneToOne
    @JoinColumn(name = "wishlistNo")
    private Wishlist wishlist;

    @OneToOne
    @JoinColumn(name = "purchaseOrderNo")
    private PurchaseOrder purchaseOrder;

    @OneToOne
    @JoinColumn(name = "saleOrderNo")
    private SaleOrder saleOrder;

    @Builder
    private Product(String productName, Category category, String productDesc,Sales_status salesStatus,Boolean imd_purchase,Boolean auction_selected,Integer buynowPrice,Integer bidSuccessPrice ,Integer auctionStartPrice, LocalDateTime auctionStartTime, LocalDateTime auctionEndTime, Member member) {
        this.productName = productName;
        this.category = category;
        this.salesStatus = salesStatus;
        this.productDesc = productDesc;
        this.imd_purchase = imd_purchase;
        this.auction_selected = auction_selected;
        this.buynowPrice = buynowPrice;
        this.auctionStartPrice = auctionStartPrice;
        this.auctionStartTime = auctionStartTime;
        this.auctionEndTime = auctionEndTime;
        this.bidSuccessPrice = bidSuccessPrice;
        this.member = member;
    }

    public static Product addProductBoth(ProductReqDto productReqDto,Member member){
        return Product.builder()
                .productName(productReqDto.getProductName())
                .category(Category.valueOf(productReqDto.getCategory()))
                .salesStatus(Sales_status.valueOf(productReqDto.getSalesStatus()))
                .productDesc(productReqDto.getProductDesc())
                .imd_purchase(productReqDto.getImd_purchase())
                .auction_selected(productReqDto.getAuction_selected())
                .buynowPrice(productReqDto.getBuynowPrice())
                .auctionStartPrice(productReqDto.getAuctionStartPrice())
                .auctionStartTime(productReqDto.getAuctionStartTime())
                .auctionEndTime(productReqDto.getAuctionEndTime())
                .bidSuccessPrice(productReqDto.getBidSuccessPrice())
                .member(member)
                .build();
    }

    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addAuction(Auction auction){
        if(auction.getProduct() != null){
            auction.getProduct().getAucitons().remove(auction);
        }

        auction.setProduct(this);
        this.aucitons.add(auction);
    }

    public void addReview(Review review){
        review.setReview_no(this);
        this.review = review;
    }

    public void addWishlist(Wishlist wishlist){
        wishlist.setWishlist_no(this);
        this.wishlist = wishlist;
    }

    public void addPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.setPurchaseOrderNo(this);
        this.purchaseOrder = purchaseOrder;
    }

    public void addSaleOrder(SaleOrder saleOrder){
        saleOrder.setSaleOrderNO(this);
        this.saleOrder = saleOrder;
    }

    public void addProductImage(ProductImage productImage){
        if(productImage.getProduct() != null){
            productImage.getProduct().getProductImages().remove(productImage);
        }

        productImage.setProduct(this);
        this.productImages.add(productImage);
    }

    public void addProductReport(ProductReport productReport){
        if(productReport.getProduct() != null){
            productReport.getProduct().getProductImages().remove(productReport);
        }

        productReport.setProduct(this);
        this.productReports.add(productReport);
    }

}