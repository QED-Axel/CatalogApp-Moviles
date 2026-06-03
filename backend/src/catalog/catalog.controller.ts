import { Controller, Get, Post, Body, Param, Put, Delete, HttpCode, HttpStatus, ParseIntPipe, Query, Headers } from '@nestjs/common';
import { CatalogService } from './catalog.service';
import { CreateMediaItemDto } from './dto/create-media-item.dto';
import { UpdateMediaItemDto } from './dto/update-media-item.dto';

@Controller('catalog')
export class CatalogController {
  constructor(private readonly catalogService: CatalogService) {}

  @Post()
  @HttpCode(HttpStatus.CREATED)
  create(@Body() createMediaItemDto: CreateMediaItemDto) {
    return this.catalogService.create(createMediaItemDto);
  }

  @Get()
  findAll() {
    return this.catalogService.findAll();
  }

  @Get('search')
  searchMovies(@Query('q') query?: string, @Headers('Accept-Language') language?: string) {
    return this.catalogService.searchMovies(query || '', language);
  }

  @Get('trending')
  getTrending(@Headers('Accept-Language') language?: string) {
    return this.catalogService.getTrending(language);
  }

  @Get(':id')
  findOne(@Param('id', ParseIntPipe) id: number) {
    return this.catalogService.findOne(id);
  }

  @Put(':id')
  update(@Param('id', ParseIntPipe) id: number, @Body() updateMediaItemDto: UpdateMediaItemDto) {
    return this.catalogService.update(id, updateMediaItemDto);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  remove(@Param('id', ParseIntPipe) id: number) {
    return this.catalogService.remove(id);
  }

  @Post('sync-tmdb')
  @HttpCode(HttpStatus.OK)
  async syncTmdb() {
    const result = await this.catalogService.syncTmdb();
    return { message: 'Sincronización completada exitosamente', result };
  }


}
