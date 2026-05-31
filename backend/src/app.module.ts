import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { CatalogModule } from './catalog/catalog.module';
import { UsersModule } from './users/users.module';
import { PrismaModule } from './prisma/prisma.module';

@Module({
  imports: [PrismaModule, CatalogModule, UsersModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
