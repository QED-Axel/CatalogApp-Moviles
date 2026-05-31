import { IsString, IsInt, IsOptional, IsUrl, Min } from 'class-validator';

export class CreateMediaItemDto {
  @IsString()
  title: string;

  @IsString()
  type: string; // 'movie' o 'book'

  @IsString()
  synopsis: string;

  @IsOptional()
  @IsUrl()
  imageUrl?: string;

  @IsInt()
  @Min(1000)
  year: number;

  @IsString()
  genre: string;
}
