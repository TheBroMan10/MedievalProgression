package com.thebroman10.medievalprogression.block;

import com.mojang.serialization.MapCodec;
import com.thebroman10.medievalprogression.block.entity.AlloyFurnaceBlockEntity;
import com.thebroman10.medievalprogression.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;


public class AlloyFurnaceBlock extends BaseEntityBlock {


    public static final Property<Direction> FACING =
            BlockStateProperties.HORIZONTAL_FACING;


    public static final Property<Boolean> LIT =
            BlockStateProperties.LIT;


    public AlloyFurnaceBlock(Properties properties) {
        super(
                properties.lightLevel(state ->
                        state.getValue(LIT) ? 15 : 0
                )
        );

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(LIT, false)
        );
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AlloyFurnaceBlock::new);
    }


    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder
    ) {
        builder.add(FACING, LIT);
    }


    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {

        return this.defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection().getOpposite()
                );
    }


    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {

        return new AlloyFurnaceBlockEntity(pos, state);
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {

        return createTickerHelper(
                type,
                ModBlockEntities.ALLOY_FURNACE,
                AlloyFurnaceBlockEntity::tick
        );
    }


    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {

        if (!level.isClientSide()) {

            BlockEntity blockEntity =
                    level.getBlockEntity(pos);


            if (blockEntity instanceof AlloyFurnaceBlockEntity furnace) {

                ((ServerPlayer) player).openMenu(furnace);

            }
        }

        return InteractionResult.SUCCESS;
    }
}