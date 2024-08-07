package com.ssg.bidssgket.user.domain.product.view;

import com.ssg.bidssgket.user.domain.product.api.dto.request.RegistProductReqDto;
import com.ssg.bidssgket.user.domain.product.application.ProductService;
import com.ssg.bidssgket.user.domain.product.view.dto.request.ProductReqDto;
import com.ssg.bidssgket.user.domain.product.view.dto.response.ProductResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 여기는 view
 */

@Slf4j
@Controller
@RequestMapping("/user/product")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/register")
    public String registController(Model model) {
        model.addAttribute("registProductReqDto",RegistProductReqDto.builder().build());
        return "user/product/register";
    }

    @PostMapping("/register")
    public String registProduct(@ModelAttribute RegistProductReqDto registProductReqDto,
                                @RequestParam("productImages")List<MultipartFile> productImages) {
        productService.registProduct(registProductReqDto, productImages);
        // fileDto -> entity로 바꿔서 DB에 파일 저장
        return "redirect:/user/main/mainpage";
    }

    @GetMapping("/update/{productNo}")
    public String updatePageController(Model model, @PathVariable("productNo") Long productNo) {
        log.info("productNo: {}", productNo);
        ProductResDto product = productService.findProductByNo(productNo);
        model.addAttribute("product", product);
        return "user/product/update";
    }

    @PostMapping("/update/{productNo}")
    public String updateProduct(@PathVariable("productNo") Long productNo,
                                @ModelAttribute ProductReqDto productReqDto) {
        productService.updateProduct(productReqDto);
        return "redirect:/user/main/mainpage";
    }


    @GetMapping("/bidFailed")
    public String bidFailedController() {
        return "/user/product/bidFailed";
    }

    @GetMapping("/bidSuccess")
    public String bidSuccessController() {
        return "/user/product/bidSuccess";
    }

    @GetMapping("/detailAuction")
    public String detailAuctionController() {
        return "/user/product/detailAuction";
    }

    @GetMapping("/detailBuyer")
    public String detailBuyerController() {
        return "/user/product/detailBuyer";
    }

    @GetMapping("/detailSeller")
    public String detailSellerController() {
        return "/user/product/detailSeller";
    }

}
