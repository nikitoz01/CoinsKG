package kg.mobile.coins.util

import kg.mobile.coins.retrofit.dto.CategoryDto
import kg.mobile.coins.retrofit.dto.CoinDto
import kg.mobile.coins.retrofit.dto.ImageDto
import kg.mobile.coins.room.model.Category
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.room.model.Image


fun Category.categoryModelToDTO(): CategoryDto {
   return with(this) {
       CategoryDto(
           id = id,
           name = name,
           parentId = parentId,
           period = period,
           description = description,
           detailURL = detailURL,
           updateTime = updateTime,
           isActive = isActive
       )
   }
}

fun Coin.coinModelToDTO(): CoinDto {
    return with(this) {
        CoinDto(
            id = id,
            categoryId = categoryId,
            name = name,
            zenoId = zenoId,
            imageUrl = imageUrl,
            rarity = rarity,
            estimate = estimate,
            auctionURL = auctionURL,
            year = year,
            size = size,
            weight = weight,
            metal = metal,
            mint = mint,
            denomination = denomination,
            description = description,
            descriptionDetailURL = descriptionDetailURL,
            updateTime = updateTime,
            isActive = isActive
        )
    }
}

fun Image.imageModelToDTO(): ImageDto {
    return with(this) {
        ImageDto(
            id = id,
            url = url,
            updateTime = updateTime
        )
    }
}

fun CategoryDto.categoryDtoToModel(): Category {
    return with(this) {
        Category(
            id = id,
            name = name,
            parentId = parentId,
            period = period,
            description = description,
            detailURL = detailURL,
            updateTime = updateTime,
            isActive = isActive
        )
    }
}

fun CoinDto.coinDtoToModel(): Coin {
    return with(this) {
        Coin(
            id = id,
            categoryId = categoryId,
            name = name,
            zenoId = zenoId,
            imageUrl = imageUrl,
            rarity = rarity,
            estimate = estimate,
            auctionURL = auctionURL,
            year = year,
            size = size,
            weight = weight,
            metal = metal,
            mint = mint,
            denomination = denomination,
            description = description,
            descriptionDetailURL = descriptionDetailURL,
            updateTime = updateTime,
            isActive = isActive
        )
    }
}

fun ImageDto.imageDtoToModel(): Image {
    return with(this) {
        Image(
            id = id,
            url = url,
            updateTime = updateTime
        )
    }
}
